/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;



/**
 *
 * @author Rodolfo
 */
public class viewEmpregado {

    public void cabecalho() {
        System.out.println("NOME    |   CPF     |   DATANASC    "
                + "|   ENDERECO    |   SEXO    |   NDEP |");
    }

    public void exibiLista(String string) {
        System.out.println(string);
    }

}
