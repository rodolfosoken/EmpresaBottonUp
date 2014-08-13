/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import controle.exceptions.NonexistentEntityException;
import controle.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Dependente;
import modelo.DependentePK;
import modelo.Empregado;

/**
 *
 * @author Rodolfo
 */
public class DependenteJpaController implements Serializable {

    public DependenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dependente dependente) throws PreexistingEntityException, Exception {
        if (dependente.getDependentePK() == null) {
            dependente.setDependentePK(new DependentePK());
        }
        dependente.getDependentePK().setCpf(dependente.getEmpregado().getCpf());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empregado empregado = dependente.getEmpregado();
            if (empregado != null) {
                empregado = em.getReference(empregado.getClass(), empregado.getCpf());
                dependente.setEmpregado(empregado);
            }
            em.persist(dependente);
            if (empregado != null) {
                empregado.getDependenteCollection().add(dependente);
                empregado = em.merge(empregado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDependente(dependente.getDependentePK()) != null) {
                throw new PreexistingEntityException("Dependente " + dependente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dependente dependente) throws NonexistentEntityException, Exception {
        dependente.getDependentePK().setCpf(dependente.getEmpregado().getCpf());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dependente persistentDependente = em.find(Dependente.class, dependente.getDependentePK());
            Empregado empregadoOld = persistentDependente.getEmpregado();
            Empregado empregadoNew = dependente.getEmpregado();
            if (empregadoNew != null) {
                empregadoNew = em.getReference(empregadoNew.getClass(), empregadoNew.getCpf());
                dependente.setEmpregado(empregadoNew);
            }
            dependente = em.merge(dependente);
            if (empregadoOld != null && !empregadoOld.equals(empregadoNew)) {
                empregadoOld.getDependenteCollection().remove(dependente);
                empregadoOld = em.merge(empregadoOld);
            }
            if (empregadoNew != null && !empregadoNew.equals(empregadoOld)) {
                empregadoNew.getDependenteCollection().add(dependente);
                empregadoNew = em.merge(empregadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DependentePK id = dependente.getDependentePK();
                if (findDependente(id) == null) {
                    throw new NonexistentEntityException("The dependente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DependentePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dependente dependente;
            try {
                dependente = em.getReference(Dependente.class, id);
                dependente.getDependentePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dependente with id " + id + " no longer exists.", enfe);
            }
            Empregado empregado = dependente.getEmpregado();
            if (empregado != null) {
                empregado.getDependenteCollection().remove(dependente);
                empregado = em.merge(empregado);
            }
            em.remove(dependente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dependente> findDependenteEntities() {
        return findDependenteEntities(true, -1, -1);
    }

    public List<Dependente> findDependenteEntities(int maxResults, int firstResult) {
        return findDependenteEntities(false, maxResults, firstResult);
    }

    private List<Dependente> findDependenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dependente.class));
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

    public Dependente findDependente(DependentePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dependente.class, id);
        } finally {
            em.close();
        }
    }

    public int getDependenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dependente> rt = cq.from(Dependente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
