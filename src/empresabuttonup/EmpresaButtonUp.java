/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empresabuttonup;

import controle.FuncionarioDAO;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import view.ViewFuncionario;



/**
 *
 * @author Rodolfo
 */
public class EmpresaButtonUp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("empresaButtonUpPU");
        ViewFuncionario view = new ViewFuncionario();
        FuncionarioDAO dao = new FuncionarioDAO(emf, view);
        dao.exibiLista();

    }

}
