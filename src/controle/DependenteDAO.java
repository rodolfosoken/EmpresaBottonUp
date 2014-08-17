/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    }

    public DependenteDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void viewToModel(){
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

    public void insere() {
        viewToModel();
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(getDependente());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
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

    /**
     * @return the dependente
     */
    public Dependente getDependente() {
        return dependente;
    }

}
