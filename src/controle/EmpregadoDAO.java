/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import modelo.Departamento;
import modelo.Dependente;
import modelo.Empregado;
import view.viewEmpregado;

/**
 *
 * @author Rodolfo
 */
public class EmpregadoDAO implements Serializable {

    /**
     * @return the emf
     */
    public EntityManager getEm() {
        return emf.createEntityManager();
    }

    private EntityManagerFactory emf = null;
    private viewEmpregado view = null;

    public EmpregadoDAO(EntityManagerFactory emf , viewEmpregado view) {
        this.emf = emf;
        this.view = view;
    }

    public void insere(Empregado empregado) {
        if (empregado.getDependenteCollection() == null) {
            empregado.setDependenteCollection(new ArrayList<>());
        }
        EntityManager em = getEm();
        em.getTransaction().begin();
        try {
            em.persist(empregado);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void remove(String cpf) throws NonexistentEntityException {
        EntityManager em = getEm();
        em.getTransaction().begin();
        Empregado empregado;
        try {
            empregado = em.getReference(Empregado.class, cpf);
        } catch (EntityNotFoundException ex) {
            throw new NonexistentEntityException("Empregado com CPF: " + cpf + "nao existe", ex);
        }

        DependenteDAO contDepe = new DependenteDAO(emf);
        Collection<Dependente> dependente = empregado.getDependenteCollection();
        for (Dependente dep : dependente) {
            contDepe.remove(dep.getDependentePK());
        }

        Departamento departamento = empregado.getNdep();
        departamento.getEmpregadoCollection().remove(empregado);
        departamento = em.merge(departamento);

        em.remove(empregado);
        em.getTransaction().commit();
        System.out.println("Empregado com CPF: " + cpf + " removido com sucesso!");
    }

    public Empregado getEmpregado(String cpf) {
        EntityManager em = getEm();
        try {
            return em.find(Empregado.class, cpf);
        } finally {
            em.close();
        }
    }

    private List<Empregado> listaEmpregado() {
        EntityManager em = getEm();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empregado.class));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public void exibiLista(){
        List<Empregado> lista  = listaEmpregado();
        view.cabecalho();
        for (Empregado empregado : lista) {
            String string = empregado.getNome() + "\t|\t" + empregado.getCpf()
                    + "\t|\t" + empregado.getDatanasc() + "\t |\t"
                    + empregado.getEndereco() + "\t |\t" + empregado.getSexo()
                    + "\t |\t" + empregado.getNdep();
            view.exibiLista(string);
        }
        
    }
        
    
    public void inProjeto(Empregado empregado){
        
    }

}
