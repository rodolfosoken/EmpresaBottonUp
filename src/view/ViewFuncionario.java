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
public class ViewFuncionario {

    private int op;
    private String nome;
    private String cpf;
    private String datanasc;
    private String endereco;
    private String sexo;
    private int nDep;
    private List<ViewDependente> viewDependente = new ArrayList<ViewDependente>();

    public ViewFuncionario() {
        getOpcoes();
    }

    public void getOpcoes() {
        while (true) {
            System.out.println("1- Inserir Funcionario || 2- Remover Funcionario || "
                    + "3- Listar todos || 4 - Todos que trabalharam com o funcionario de cpf: || 5- Dados do Funcionario com Cpf: || 6- Sair");
            Scanner sc = new Scanner(System.in);
            setOp(sc.nextInt());

            switch (getOp()) {
                case 1:
                    Scanner sc2 = new Scanner(System.in);
                    System.out.println("Insira o nome:");
                    setNome(sc2.nextLine());
                    System.out.println("Insira o CPF:");
                    setCpf(sc2.nextLine());
                    System.out.println("Insira o Endereço:");
                    setEndereco(sc2.nextLine());
                    System.out.println("Insira a data de nascimento:");
                    setDatanasc(sc2.nextLine());
                    System.out.println("Insira o sexo:");
                    setSexo(sc2.nextLine());
                    System.out.println("Insira o numero do departamento:");
                    setnDep(sc2.nextInt());
                    System.out.println("Ha dependentes? (y\\n): ");
                    Scanner scdep = new Scanner(System.in);
                    if (scdep.nextLine().equals("y")) {
                        System.out.println("Quantos dependentes?: ");
                        Scanner sc3 = new Scanner(System.in);
                        int num  = sc3.nextInt();
                        while(num >= 1){
                        ViewDependente dependente = new ViewDependente(this);
                        getViewDependente().add(dependente);
                        num--;
                        }
                    }
                    return;

                case 2:
                    System.out.println("Insira o CPF do Funcionario: ");
                    Scanner sc1 = new Scanner(System.in);
                    setCpf(sc1.nextLine());
                    return;
                    
                case 3:
                    System.out.println("Funcionarios da empresa:");
                    return;
                    
                case 4:
                    Scanner sc3 = new Scanner(System.in);
                    setCpf(sc3.nextLine());
                    return;

                case 5:
                    Scanner sc4 = new Scanner(System.in);
                    setCpf(sc4.nextLine());
                    return;
                    
                case 6:
                    return;

            }
        }
    }

    public void cabecalho() {
        System.out.println("NOME    |       CPF              |      DATANASC    "
                + "     |      ENDERECO                |       SEXO     |   NDEP |");
    }

    public void exibiLinha(String string) {
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


    /**
     * @return the viewDependente
     */
    public List<ViewDependente> getViewDependente() {
        return viewDependente;
    }

    /**
     * @param viewDependente the viewDependente to set
     */
    public void setViewDependente(List<ViewDependente> viewDependente) {
        this.viewDependente = viewDependente;
    }

    /**
     * @return the op
     */
    public int getOp() {
        return op;
    }

    /**
     * @param op the op to set
     */
    public void setOp(int op) {
        this.op = op;
    }

}
