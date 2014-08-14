/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empresabuttonup;

import controle.FuncionarioDAO;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Funcionario;
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
        Funcionario funcionario = dao.getFuncionario("66666666");
        List <Funcionario> selecionados = dao.inProject(funcionario);
        dao.exibiLista(selecionados);
    }

}
