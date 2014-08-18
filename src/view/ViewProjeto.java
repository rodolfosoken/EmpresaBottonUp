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
public class ViewProjeto {

    private int opcao;
    private String nome;
    private int numero;

    public ViewProjeto() {
        listaOpcoes();
    }

    public void listaOpcoes() {
        System.out.println("1-Adicionar Projeto || 2- Listar Todos os Projetos || 3- Sair");
        Scanner sc = new Scanner(System.in);
        setOpcao(sc.nextInt());
        switch(getOpcao()){
            case  1:
                System.out.println("Insira o nome do projeto: ");
                Scanner sc1 = new Scanner(System.in);
                setNome(sc1.nextLine());
                System.out.println("Insira o numero do projeto: ");
                Scanner sc2 = new Scanner(System.in);
                setNumero(sc2.nextInt());
                break;
                
            case 2:
                break;
                
            case 3:
                return;
            
        }
        
    }
    
    public void cabecalho(){
        System.out.println("|             Nome                   |       Numero      |");
    }
    
    public void exibiLinha(String string){
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
