/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Scanner;

/**
 *
 * @author Rodolfo
 */
public class ViewDependente {
    
    private int opcao;
    private ViewFuncionario funcionario;
    private String datanasc;
    private String sexo;
    private String nome;

    public ViewDependente(ViewFuncionario viewFunc) {
        this.funcionario = viewFunc;
        insere();
    }

    public ViewDependente() {
        getOpcoes();
    }

    public void getOpcoes() {
        System.out.println("1- Adicionar dependente || 2- Remover || 3- Listar todos os dependentes || 4- Sair ");
        Scanner sc = new Scanner(System.in);
        switch(getOpcao()){
            case 1:
                System.out.println("Insira o cpf do Funcionario: ");
                Scanner sc1 = new Scanner(System.in);
                funcionario.setCpf(sc1.nextLine());
                insere();
                return;
                
            case 2:
                System.out.println("Insira o Nome do dependente: ");
                Scanner sc2 = new Scanner(System.in);
                setNome(sc2.nextLine());
                System.out.println("Insira o CPF do Funcionario: ");
                Scanner sc3 = new Scanner(System.in);
                funcionario.setCpf(sc3.nextLine());
                return;
                
            case 3:
                return;
                
            case 4:
                return;
                
        }
        
    }
    
    public void insere(){
        System.out.println("Insira o nome do dependente: ");
        Scanner sc = new Scanner(System.in);
        setNome(sc.nextLine());
        System.out.println("Insira a data de nascimento: ");
        Scanner sc1 = new Scanner(System.in);
        setDatanasc(sc1.nextLine());
        System.out.println("Insira o sexo: ");
        Scanner sc2 = new Scanner(System.in);
        setSexo(sc2.nextLine());
    }

    /**
     * @return the funcionario
     */
    public ViewFuncionario getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(ViewFuncionario funcionario) {
        this.funcionario = funcionario;
    }

    /**
     * @return the datanasc
     */
    public String getDatanasc() {
        return datanasc;
    }

    /**
     * @param datanasc the datanasc to set
     */
    public void setDatanasc(String datanasc) {
        this.datanasc = datanasc;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
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

}
