/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import view.ViewDepartamento;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Departamento;

/**
 *
 * @author Rodolfo
 */
public class DepartamentoDAO {
    
    private EntityManagerFactory emf;
    private Departamento departamento;
    private ViewDepartamento view;
          
    public DepartamentoDAO(EntityManagerFactory emf, ViewDepartamento view ,Departamento model){
        this.emf = emf;
        this.departamento = model;
        this.view = view;
    }
    
    public DepartamentoDAO(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public Departamento getDepartamento(int numeroDep){
        EntityManager em = getEntityManager();
        try{
            return em.find(Departamento.class, numeroDep);
        }finally{
            em.close();
        }
    }
    
    
    
    
}
