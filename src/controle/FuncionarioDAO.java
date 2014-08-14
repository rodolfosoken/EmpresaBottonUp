/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.NonexistentEntityException;
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
import modelo.Funcionario;
import modelo.Projeto;
import view.ViewFuncionario;

/**
 *
 * @author Rodolfo
 */
public class FuncionarioDAO {

    public EntityManager getEm() {
        return emf.createEntityManager();
    }

    private EntityManagerFactory emf = null;
    private ViewFuncionario view = null;

    public FuncionarioDAO(EntityManagerFactory emf, ViewFuncionario view) {
        this.emf = emf;
        this.view = view;
    }

    public void insere(Funcionario funcionario) {
        if (funcionario.getDependenteCollection() == null) {
            funcionario.setDependenteCollection(new ArrayList<>());
        }
        EntityManager em = getEm();
        em.getTransaction().begin();
        try {
            em.persist(funcionario);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void remove(String cpf) throws NonexistentEntityException {
        EntityManager em = getEm();
        em.getTransaction().begin();
        Funcionario funcionario;
        try {
            funcionario = em.getReference(Funcionario.class, cpf);
        } catch (EntityNotFoundException ex) {
            throw new NonexistentEntityException("Funcionario com CPF: " + cpf + "nao existe", ex);
        }

        DependenteDAO contDepe = new DependenteDAO(emf);
        Collection<Dependente> dependente = funcionario.getDependenteCollection();
        for (Dependente dep : dependente) {
            contDepe.remove(dep.getDependentePK());
        }

        Departamento departamento = funcionario.getNdep();
        departamento.getFuncionarioCollection().remove(funcionario);
        departamento = em.merge(departamento);

        em.remove(funcionario);
        em.getTransaction().commit();
        System.out.println("Empregado com CPF: " + cpf + " removido com sucesso!");
    }

    public Funcionario getFuncionario(String cpf) {
        EntityManager em = getEm();
        try {
            return em.find(Funcionario.class, cpf);
        } finally {
            em.close();
        }
    }

    private List<Funcionario> listaFuncionario() {
        EntityManager em = getEm();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionario.class));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public void exibiLista() {
        List<Funcionario> lista = listaFuncionario();
        view.cabecalho();
        for (Funcionario funcionario : lista) {
            String string = funcionario.getNome() + "\t|\t" + funcionario.getCpf()
                    + "\t|\t" + funcionario.getDatanasc() + "\t |\t"
                    + funcionario.getEndereco() + "\t |\t" + funcionario.getSexo()
                    + "\t |\t" + funcionario.getNdep();
            view.exibiLista(string);
        }

    }

    public void exibiLista(List<Funcionario> lista) {
        view.cabecalho();
        for (Funcionario funcionario : lista) {
            String string = funcionario.getNome() + "\t|\t" + funcionario.getCpf()
                    + "\t|\t" + funcionario.getDatanasc() + "\t |\t"
                    + funcionario.getEndereco() + "\t |\t" + funcionario.getSexo()
                    + "\t |\t" + funcionario.getNdep().getNumero();
            view.exibiLista(string);
        }

    }

    public List<Funcionario> inProject(Funcionario funcRef) {
        List<Funcionario> todos = listaFuncionario();
        List<Funcionario> selecionados = new ArrayList<>();
        for (Funcionario funcionario : todos) {
            Collection<Projeto> projetos_func = funcionario.getProjetoCollection();
            if (!(projetos_func.isEmpty() || funcionario.getCpf().equals(funcRef.getCpf()))) {
                if (projetos_func.containsAll(funcRef.getProjetoCollection())) {
                    selecionados.add(funcionario);
                }
            }
        }
        return selecionados;
    }

}
