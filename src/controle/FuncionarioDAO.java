/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.NonexistentEntityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import modelo.Departamento;
import modelo.Dependente;
import modelo.Funcionario;
import modelo.Projeto;
import view.ViewDependente;
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
    private Funcionario funcionario = null;

    public FuncionarioDAO(EntityManagerFactory emf, ViewFuncionario view, Funcionario model) {
        this.emf = emf;
        this.view = view;
        this.funcionario = model;

        //faz o papel do ActionListener
        switch (view.getOp()) {
            case 1:
                viewToModel();
                insere();
                System.out.println("Funcionario cadastrado com sucesso!");
                break;

            case 2:
                try {
                    remove(view.getCpf());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Empregado com CPF: " + view.getCpf() + " foi removido com sucesso!");
                break;

            case 3:
                exibiLista();
                break;

            case 4:
                funcionario = getFuncionario(view.getCpf());
                exibiLista(inProject(funcionario));
                break;

            case 5:
                funcionario = getFuncionario(view.getCpf());
                exibiFuncionario();
                break;

        }
    }
    
    public FuncionarioDAO(EntityManagerFactory emf){
        this.emf = emf;
        
    }

    public void viewToModel() {
        funcionario.setCpf(view.getCpf());
        funcionario.setEndereco(view.getEndereco());
        funcionario.setNome(view.getNome());
        funcionario.setSexo(view.getSexo().toCharArray()[0]);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            funcionario.setDatanasc((Date) df.parse(view.getDatanasc()));
        } catch (ParseException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        DepartamentoDAO daoDep = new DepartamentoDAO(emf);
        funcionario.setNdep(daoDep.findDepartamento(view.getnDep()));
        funcionario.setDependenteCollection(new ArrayList<>());
        funcionario.setProjetoCollection(new ArrayList<>());
        for (ViewDependente viewDep : view.getViewDependente()) {
            Dependente dependente = new Dependente();
            DependenteDAO daoDependente = new DependenteDAO(emf, viewDep, dependente);
            daoDependente.viewToModel();
            funcionario.getDependenteCollection().add(daoDependente.getDependente());
        }
        for (int numero : view.getNumProjetos()) {
            Projeto proj = new Projeto();
            ProjetoDAO daoProj = new ProjetoDAO(emf);
            proj = daoProj.findProjeto(numero);
            funcionario.getProjetoCollection().add(proj);
        }
        

    }

    public void insere() {
        viewToModel();
        if (funcionario.getDependenteCollection() == null) {
            funcionario.setDependenteCollection(new ArrayList<>());
        }
        if (funcionario.getProjetoCollection() == null) {
            funcionario.setProjetoCollection(new ArrayList<>());
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
        //Funcionario funcionario;
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
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (Funcionario funcionario1 : lista) {
            String string = funcionario1.getNome() + "\t|\t" + funcionario1.getCpf()
                    + "\t|\t" + df.format(funcionario1.getDatanasc()) + "\t |\t"
                    + funcionario1.getEndereco() + "  \t|\t" + funcionario1.getSexo()
                    + "\t |\t" + funcionario1.getNdep().getNumero();
            view.exibiLinha(string);
        }

    }

    public void exibiLista(List<Funcionario> lista) {
        view.cabecalho();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (Funcionario funcionario1 : lista) {
            String string = funcionario1.getNome() + "\t|\t" + funcionario1.getCpf()
                    + "\t|\t" + df.format(funcionario1.getDatanasc()) + "\t |\t"
                    + funcionario1.getEndereco() + "  \t |\t" + funcionario1.getSexo()
                    + "\t |\t" + funcionario1.getNdep().getNumero();
            view.exibiLinha(string);
        }

    }

    public void exibiFuncionario() {
        view.cabecalho();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String string = funcionario.getNome() + "\t|\t" + funcionario.getCpf()
                + "\t|\t" + df.format(funcionario.getDatanasc()) + "\t |\t"
                + funcionario.getEndereco() + "  \t |\t" + funcionario.getSexo()
                + "\t |\t" + funcionario.getNdep().getNumero();
        view.exibiLinha(string);
    }

    public List<Funcionario> inProject(Funcionario funcRef) {
        List<Funcionario> todos = listaFuncionario();
        List<Funcionario> selecionados = new ArrayList<>();
        for (Funcionario funcionario1 : todos) {
            Collection<Projeto> projetos_func = funcionario1.getProjetoCollection();
            if (!(projetos_func.isEmpty() || funcionario1.getCpf().equals(funcRef.getCpf()))) {
                if (projetos_func.containsAll(funcRef.getProjetoCollection())) {
                    selecionados.add(funcionario1);
                }
            }
        }
        return selecionados;
    }

    public boolean existe(String cpf) {
        boolean existe = false;
        EntityManager em = getEm();
        try {
            if (em.find(Funcionario.class, cpf) != null) {
                existe = true;
            }
        } finally {
            em.close();
        }

        return existe;

    }

}
