/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import modelo.Dependente;
import modelo.DependentePK;
import modelo.Funcionario;
import view.ViewDependente;

/**
 *
 * @author Rodolfo
 */
public class DependenteDAO {

    private EntityManagerFactory emf;
    private Dependente dependente;
    private DependentePK dependentePK = new DependentePK();
    private ViewDependente view;

    public DependenteDAO(EntityManagerFactory emf, ViewDependente view, Dependente model) {
        this.emf = emf;
        this.dependente = model;
        this.view = view;
        
        //faz o papel do listener
        switch(view.getOpcao()){
            case 1:
                viewToModel(view.getCpf());
                insere();
                System.out.println("Dependente " + view.getNome() + " inserido com sucesso!");
                break;
            case 2:
                dependentePK.setCPFFunc(view.getCpf());
                dependentePK.setNome(view.getNome());
                this.dependente = find(dependentePK);
                remove();
                System.out.println("Dependente " + view.getNome() + " removido com sucesso!");
                break;
                
            case 3:
                exibiLista();
                return;
        }
    }

    public DependenteDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void viewToModel() {
        dependentePK.setNome(view.getNome());
        dependentePK.setCPFFunc(view.getFuncionario().getCpf());
        getDependente().setDependentePK(dependentePK);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            getDependente().setDatanasc((Date) df.parse(view.getDatanasc()));
        } catch (ParseException ex) {
            Logger.getLogger(DependenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        FuncionarioDAO daoFuncionario = new FuncionarioDAO(emf);
        getDependente().setFuncionario(daoFuncionario.getFuncionario(view.getFuncionario().getCpf()));
        getDependente().setSexo(view.getSexo().toCharArray()[0]);
    }
    
        public void viewToModel(String CPF) {
        dependentePK.setNome(view.getNome());
        dependentePK.setCPFFunc(CPF);
        getDependente().setDependentePK(dependentePK);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            getDependente().setDatanasc((Date) df.parse(view.getDatanasc()));
        } catch (ParseException ex) {
            Logger.getLogger(DependenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        FuncionarioDAO daoFuncionario = new FuncionarioDAO(emf);
        getDependente().setFuncionario(daoFuncionario.getFuncionario(CPF));
        getDependente().setSexo(view.getSexo().toCharArray()[0]);
    }

    public void insere() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(getDependente());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void remove() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Dependente dependente = em.getReference(Dependente.class, dependentePK);
        Funcionario funcionario = dependente.getFuncionario();
        funcionario.getDependenteCollection().remove(dependente);
        funcionario = em.merge(funcionario);
        em.remove(dependente);
        em.getTransaction().commit();
        em.close();

    }
    
        public void remove(DependentePK id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Dependente dependente = em.getReference(Dependente.class, id);
        Funcionario funcionario = dependente.getFuncionario();
        funcionario.getDependenteCollection().remove(dependente);
        funcionario = em.merge(funcionario);
        em.remove(dependente);
        em.getTransaction().commit();
        em.close();

    }

    public void exibiLista() {
        List<Dependente> lista = listaDependentes();
        view.cabecalho();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (Dependente dependente1 : lista) {
            String string = dependente1.getDependentePK().getNome() + "\t|\t" + 
                    dependente1.getDependentePK().getCPFFunc() + "\t|\t" + df.format(dependente1.getDatanasc()) + "\t|\t"
                    + dependente1.getSexo() + "   |";
            view.exibiLinha(string);
        }
    }
    
    
    private Dependente find(DependentePK pk){
        EntityManager em = getEntityManager();
        try{
            return em.find(Dependente.class, pk);
        }finally{
            em.close();
        }
        
    }

    private List<Dependente> listaDependentes() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dependente.class));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * @return the dependente
     */
    public Dependente getDependente() {
        return dependente;
    }

}
