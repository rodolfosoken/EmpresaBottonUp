/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empresabuttonup;

import controle.DepartamentoDAO;
import controle.DependenteDAO;
import controle.FuncionarioDAO;
import java.util.Scanner;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Departamento;
import modelo.Dependente;
import modelo.Funcionario;
import view.ViewDepartamento;
import view.ViewDependente;
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
        int op = 0;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1- Funcionario || 2- Dependente || 3 - Projeto || 4- Departamento || 5-Sair");
            op = sc.nextInt();

            switch (op) {
                case 1:
                    ViewFuncionario view = new ViewFuncionario();
                    Funcionario model = new Funcionario();
                    FuncionarioDAO dao = new FuncionarioDAO(emf, view, model);
                    break;
                    
                case 2:
                    ViewDependente viewDependente = new ViewDependente();
                    Dependente modelDependente = new Dependente();
                    DependenteDAO daoDependente = new DependenteDAO(emf, viewDependente, modelDependente);
                    break;

                case 4:
                    ViewDepartamento viewDepartamento = new ViewDepartamento();
                    Departamento modelDepto = new Departamento();
                    DepartamentoDAO daoDepto = new DepartamentoDAO(emf, viewDepartamento, modelDepto);
                    break;
                    
                case 5:
                    return;
            }
        }
                
    }

}
