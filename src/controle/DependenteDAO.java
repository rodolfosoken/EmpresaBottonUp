/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Dependente;
import modelo.DependentePK;
import modelo.Funcionario;

/**
 *
 * @author Rodolfo
 */
public class DependenteDAO {

    private EntityManagerFactory emf;

    public DependenteDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
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

}
