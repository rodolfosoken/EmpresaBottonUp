/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Projeto;

/**
 *
 * @author Rodolfo
 */
public class ProjetoDAO {

    private EntityManagerFactory emf;

    public ProjetoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void insere(Projeto projeto) {
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

}
