/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empresabuttonup;

import controle.EmpregadoDAO;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import view.viewEmpregado;



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
        viewEmpregado view = new viewEmpregado();
        EmpregadoDAO dao = new EmpregadoDAO(emf, view);
        dao.exibiLista();

    }

}
