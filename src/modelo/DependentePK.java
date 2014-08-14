/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Rodolfo
 */
@Embeddable
public class DependentePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @Column(name = "CPF_Func")
    private String cPFFunc;

    public DependentePK() {
    }

    public DependentePK(String nome, String cPFFunc) {
        this.nome = nome;
        this.cPFFunc = cPFFunc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPFFunc() {
        return cPFFunc;
    }

    public void setCPFFunc(String cPFFunc) {
        this.cPFFunc = cPFFunc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nome != null ? nome.hashCode() : 0);
        hash += (cPFFunc != null ? cPFFunc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DependentePK)) {
            return false;
        }
        DependentePK other = (DependentePK) object;
        if ((this.nome == null && other.nome != null) || (this.nome != null && !this.nome.equals(other.nome))) {
            return false;
        }
        if ((this.cPFFunc == null && other.cPFFunc != null) || (this.cPFFunc != null && !this.cPFFunc.equals(other.cPFFunc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DependentePK[ nome=" + nome + ", cPFFunc=" + cPFFunc + " ]";
    }
    
}
