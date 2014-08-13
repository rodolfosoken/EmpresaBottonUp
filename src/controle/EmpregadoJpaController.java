/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.IllegalOrphanException;
import controle.exceptions.NonexistentEntityException;
import controle.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Departamento;
import modelo.Dependente;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Empregado;

/**
 *
 * @author Rodolfo
 */
public class EmpregadoJpaController implements Serializable {

    public EmpregadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empregado empregado) throws PreexistingEntityException, Exception {
        if (empregado.getDependenteCollection() == null) {
            empregado.setDependenteCollection(new ArrayList<Dependente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento ndep = empregado.getNdep();
            if (ndep != null) {
                ndep = em.getReference(ndep.getClass(), ndep.getNumero());
                empregado.setNdep(ndep);
            }
            Collection<Dependente> attachedDependenteCollection = new ArrayList<Dependente>();
            for (Dependente dependenteCollectionDependenteToAttach : empregado.getDependenteCollection()) {
                dependenteCollectionDependenteToAttach = em.getReference(dependenteCollectionDependenteToAttach.getClass(),
                        dependenteCollectionDependenteToAttach.getDependentePK());
                attachedDependenteCollection.add(dependenteCollectionDependenteToAttach);
            }
            empregado.setDependenteCollection(attachedDependenteCollection);
            em.persist(empregado);
            if (ndep != null) {
                ndep.getEmpregadoCollection().add(empregado);
                ndep = em.merge(ndep);
            }
            for (Dependente dependenteCollectionDependente : empregado.getDependenteCollection()) {
                Empregado oldEmpregadoOfDependenteCollectionDependente = dependenteCollectionDependente.getEmpregado();
                dependenteCollectionDependente.setEmpregado(empregado);
                dependenteCollectionDependente = em.merge(dependenteCollectionDependente);
                if (oldEmpregadoOfDependenteCollectionDependente != null) {
                    oldEmpregadoOfDependenteCollectionDependente.getDependenteCollection().remove(dependenteCollectionDependente);
                    oldEmpregadoOfDependenteCollectionDependente = em.merge(oldEmpregadoOfDependenteCollectionDependente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpregado(empregado.getCpf()) != null) {
                throw new PreexistingEntityException("Empregado " + empregado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empregado empregado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empregado persistentEmpregado = em.find(Empregado.class, empregado.getCpf());
            Departamento ndepOld = persistentEmpregado.getNdep();
            Departamento ndepNew = empregado.getNdep();
            Collection<Dependente> dependenteCollectionOld = persistentEmpregado.getDependenteCollection();
            Collection<Dependente> dependenteCollectionNew = empregado.getDependenteCollection();
            List<String> illegalOrphanMessages = null;
            for (Dependente dependenteCollectionOldDependente : dependenteCollectionOld) {
                if (!dependenteCollectionNew.contains(dependenteCollectionOldDependente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dependente " + dependenteCollectionOldDependente
                            + " since its empregado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ndepNew != null) {
                ndepNew = em.getReference(ndepNew.getClass(), ndepNew.getNumero());
                empregado.setNdep(ndepNew);
            }
            Collection<Dependente> attachedDependenteCollectionNew = new ArrayList<Dependente>();
            for (Dependente dependenteCollectionNewDependenteToAttach : dependenteCollectionNew) {
                dependenteCollectionNewDependenteToAttach = em.getReference(dependenteCollectionNewDependenteToAttach.getClass(),
                        dependenteCollectionNewDependenteToAttach.getDependentePK());
                attachedDependenteCollectionNew.add(dependenteCollectionNewDependenteToAttach);
            }
            dependenteCollectionNew = attachedDependenteCollectionNew;
            empregado.setDependenteCollection(dependenteCollectionNew);
            empregado = em.merge(empregado);
            if (ndepOld != null && !ndepOld.equals(ndepNew)) {
                ndepOld.getEmpregadoCollection().remove(empregado);
                ndepOld = em.merge(ndepOld);
            }
            if (ndepNew != null && !ndepNew.equals(ndepOld)) {
                ndepNew.getEmpregadoCollection().add(empregado);
                ndepNew = em.merge(ndepNew);
            }
            for (Dependente dependenteCollectionNewDependente : dependenteCollectionNew) {
                if (!dependenteCollectionOld.contains(dependenteCollectionNewDependente)) {
                    Empregado oldEmpregadoOfDependenteCollectionNewDependente = dependenteCollectionNewDependente.getEmpregado();
                    dependenteCollectionNewDependente.setEmpregado(empregado);
                    dependenteCollectionNewDependente = em.merge(dependenteCollectionNewDependente);
                    if (oldEmpregadoOfDependenteCollectionNewDependente != null && !oldEmpregadoOfDependenteCollectionNewDependente.equals(empregado)) {
                        oldEmpregadoOfDependenteCollectionNewDependente.getDependenteCollection().remove(dependenteCollectionNewDependente);
                        oldEmpregadoOfDependenteCollectionNewDependente = em.merge(oldEmpregadoOfDependenteCollectionNewDependente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empregado.getCpf();
                if (findEmpregado(id) == null) {
                    throw new NonexistentEntityException("The empregado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empregado empregado;
            try {
                empregado = em.getReference(Empregado.class, id);
                empregado.getCpf();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empregado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Dependente> dependenteCollectionOrphanCheck = empregado.getDependenteCollection();
            for (Dependente dependenteCollectionOrphanCheckDependente : dependenteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empregado (" + empregado + ") cannot be destroyed since the Dependente "
                        + dependenteCollectionOrphanCheckDependente + " in its dependenteCollection field has a non-nullable empregado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento ndep = empregado.getNdep();
            if (ndep != null) {
                ndep.getEmpregadoCollection().remove(empregado);
                ndep = em.merge(ndep);
            }
            em.remove(empregado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empregado> findEmpregadoEntities() {
        return findEmpregadoEntities(true, -1, -1);
    }

    public List<Empregado> findEmpregadoEntities(int maxResults, int firstResult) {
        return findEmpregadoEntities(false, maxResults, firstResult);
    }

    private List<Empregado> findEmpregadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empregado.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Empregado findEmpregado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empregado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpregadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empregado> rt = cq.from(Empregado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
