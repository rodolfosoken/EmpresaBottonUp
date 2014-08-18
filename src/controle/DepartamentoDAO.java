/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import modelo.Departamento;
import modelo.LocaisDepto;
import view.ViewDepartamento;

/**
 *
 * @author Rodolfo
 */
public class DepartamentoDAO {

    private EntityManagerFactory emf;
    private Departamento departamento;
    private ViewDepartamento view;

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DepartamentoDAO(EntityManagerFactory emf, ViewDepartamento view, Departamento model) {
        this.emf = emf;
        this.departamento = model;
        this.view = view;

        switch (view.getOpcao()) {
            case 1:
                viewToModel();
                insere();
                System.out.println("Departamento " + view.getNome() + " inserido com sucesso!");
                break;
            case 2:
                exibiLista();
                break;

        }

    }

    public DepartamentoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void viewToModel() {
        getDepartamento().setNumero(getView().getNumero());
        getDepartamento().setNome(getView().getNome());
        getDepartamento().setFuncionarioCollection(new ArrayList<>());
        getDepartamento().setLocaisDeptoCollection(new ArrayList<>());
        for (String local : getView().getLocal()) {
            LocaisDepto locais = new LocaisDepto(view.getNumero(), local);
            getDepartamento().getLocaisDeptoCollection().add(locais);
        }

    }

    public void insere() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        if (getDepartamento().getFuncionarioCollection() == null) {
            getDepartamento().setFuncionarioCollection(new ArrayList<>());
        }
        if (getDepartamento().getLocaisDeptoCollection() == null) {
            getDepartamento().setLocaisDeptoCollection(new ArrayList<>());
        }
        try {
            em.persist(getDepartamento());
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    public Departamento findDepartamento(int numeroDep) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, numeroDep);
        } finally {
            em.close();
        }
    }
    
        public void exibiLista() {
        List<Departamento> lista = listaDepartamento();
        view.cabecalho();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (Departamento departamento1 : lista) {
            String string = departamento1.getNome() + "   \t|\t " + departamento1.getNumero() + "|\t";
            view.exibiLinha(string);
        }
    }
    

    private List<Departamento> listaDepartamento() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * @return the departamento
     */
    public Departamento getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the view
     */
    public ViewDepartamento getView() {
        return view;
    }

    /**
     * @param view the view to set
     */
    public void setView(ViewDepartamento view) {
        this.view = view;
    }

}
