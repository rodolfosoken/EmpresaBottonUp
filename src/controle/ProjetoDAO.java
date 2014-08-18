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
import modelo.Projeto;
import view.ViewProjeto;

/**
 *
 * @author Rodolfo
 */
public class ProjetoDAO {
    
    private ViewProjeto view;
    private Projeto projeto;
    private EntityManagerFactory emf;

    public ProjetoDAO(EntityManagerFactory emf, ViewProjeto view, Projeto model){
        this.projeto = model;
        this.view = view;
        this.emf = emf;
        switch(view.getOpcao()){
            case 1:
                viewToModel();
                insere();
                System.out.println("Projeto "+view.getNome()+" cadastrado com sucesso!");
                break;
                
            case 2:
                exibiLista();
                break;
                
        }
        
    }
    
    public ProjetoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return getEmf().createEntityManager();
    }
    
    public void viewToModel(){
        getProjeto().setNome(view.getNome());
        getProjeto().setNumero(view.getNumero());
    }

    public void insere() {
        if (projeto.getFuncionarioCollection() == null) {
            projeto.setFuncionarioCollection(new ArrayList<>());
        }
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(projeto);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
            public void exibiLista() {
        List<Projeto> lista = listaProjeto();
        view.cabecalho();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (Projeto projeto1 : lista) {
            String string = projeto1.getNome() + "\t.........|" + projeto1.getNumero() + "|\t";
            view.exibiLinha(string);
        }
    }
    

    private List<Projeto> listaProjeto() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Projeto.class));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Projeto findProjeto(int numero){
        EntityManager em = getEntityManager();
        try{
            return em.find(Projeto.class, numero);
        }finally{
            em.close();
        }
    }

    /**
     * @return the view
     */
    public ViewProjeto getView() {
        return view;
    }

    /**
     * @param view the view to set
     */
    public void setView(ViewProjeto view) {
        this.view = view;
    }

    /**
     * @return the projeto
     */
    public Projeto getProjeto() {
        return projeto;
    }

    /**
     * @param projeto the projeto to set
     */
    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    /**
     * @return the emf
     */
    public EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * @param emf the emf to set
     */
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

}
