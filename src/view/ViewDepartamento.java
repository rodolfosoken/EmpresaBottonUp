/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Rodolfo
 */
public class ViewDepartamento {

    private int opcao;
    private String nome;
    private int numero;
    private List<String> local = new ArrayList<>();
    private List<String> cpf_func = new ArrayList<>();

    public ViewDepartamento() {
        listaOpcoes();
    }

    public void listaOpcoes() {
        System.out.println("1- Adicionar Departamento || 2- Listar todos os departamentos || 3- Sair ");
        Scanner sc = new Scanner(System.in);
        setOpcao(sc.nextInt());
        switch (getOpcao()) {
            case 1:
                System.out.println("Insira o nome do departamento: ");
                Scanner sc1 = new Scanner(System.in);
                setNome(sc1.nextLine());
                System.out.println("Insira o numero do departamento: ");
                Scanner sc2 = new Scanner(System.in);
                setNumero(sc2.nextInt());
                System.out.println("Ha quantos locais para este departamento?: ");
                Scanner sc3 = new Scanner(System.in);
                int num = sc3.nextInt();
                while (num-- >= 1) {
                    System.out.println("Insira o local "+ num + ": ");
                    Scanner sc4 = new Scanner(System.in);
                    getLocal().add(sc4.nextLine());
                }
                break;
                
            case 2:
                break;
                
            case 3:
                return;

        }

    }
    
    
    public void cabecalho(){
        System.out.println("Nome           |   Numero  |");
    }
    
    public void exibiLinha(String string){
        System.out.println(string);
    }

    /**
     * @return the opcao
     */
    public int getOpcao() {
        return opcao;
    }

    /**
     * @param opcao the opcao to set
     */
    public void setOpcao(int opcao) {
        this.opcao = opcao;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the local
     */
    public List<String> getLocal() {
        return local;
    }

    /**
     * @param local the local to set
     */
    public void setLocal(List<String> local) {
        this.local = local;
    }

    /**
     * @return the cpf_func
     */
    public List<String> getCpf_func() {
        return cpf_func;
    }

    /**
     * @param cpf_func the cpf_func to set
     */
    public void setCpf_func(List<String> cpf_func) {
        this.cpf_func = cpf_func;
    }

}
