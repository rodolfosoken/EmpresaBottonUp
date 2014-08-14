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
public class ViewFuncionario {

    private int op;
    private String nome;
    private String cpf;
    private String datanasc;
    private String endereco;
    private Character sexo;
    private int nDep;

    public ViewFuncionario() {
        getOpcoes();
    }

    public void getOpcoes() {
        while (true) {
            System.out.println("1- Inserir Funcionario || 2- Remover Funcionario || "
                    + "3- Listar todos || 4 - Divisão de Algebra Relacional || 5- Sair");
            Scanner sc = new Scanner(System.in);
            op = sc.nextInt();

            switch (op) {
                case 1:
                    System.out.println("Insira o nome:");
                    Scanner sc2 = new Scanner(System.in);
                    String n = sc2.nextLine();
                    setNome(n);
                    break;

                case 5:
                    return;

            }
            
            System.out.println(getNome());

        }
    }

    public void cabecalho() {
        System.out.println("NOME    |   CPF     |   DATANASC    "
                + "|   ENDERECO    |   SEXO    |   NDEP |");
    }

    public void exibiLista(String string) {
        System.out.println(string);
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
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
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
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the sexo
     */
    public Character getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the nDep
     */
    public int getnDep() {
        return nDep;
    }

    /**
     * @param nDep the nDep to set
     */
    public void setnDep(int nDep) {
        this.nDep = nDep;
    }

}
