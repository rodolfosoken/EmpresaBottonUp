/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rodolfo
 */
@Entity
@Table(name = "dependente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dependente.findAll", query = "SELECT d FROM Dependente d"),
    @NamedQuery(name = "Dependente.findByNome", query = "SELECT d FROM Dependente d WHERE d.dependentePK.nome = :nome"),
    @NamedQuery(name = "Dependente.findByCPFFunc", query = "SELECT d FROM Dependente d WHERE d.dependentePK.cPFFunc = :cPFFunc"),
    @NamedQuery(name = "Dependente.findByDatanasc", query = "SELECT d FROM Dependente d WHERE d.datanasc = :datanasc"),
    @NamedQuery(name = "Dependente.findBySexo", query = "SELECT d FROM Dependente d WHERE d.sexo = :sexo")})
public class Dependente implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DependentePK dependentePK;
    @Column(name = "DATANASC")
    @Temporal(TemporalType.DATE)
    private Date datanasc;
    @Column(name = "SEXO")
    private Character sexo;
    @JoinColumn(name = "CPF_Func", referencedColumnName = "CPF", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Funcionario funcionario;

    public Dependente() {
    }

    public Dependente(DependentePK dependentePK) {
        this.dependentePK = dependentePK;
    }

    public Dependente(String nome, String cPFFunc) {
        this.dependentePK = new DependentePK(nome, cPFFunc);
    }

    public DependentePK getDependentePK() {
        return dependentePK;
    }

    public void setDependentePK(DependentePK dependentePK) {
        this.dependentePK = dependentePK;
    }

    public Date getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(Date datanasc) {
        this.datanasc = datanasc;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dependentePK != null ? dependentePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dependente)) {
            return false;
        }
        Dependente other = (Dependente) object;
        if ((this.dependentePK == null && other.dependentePK != null) || (this.dependentePK != null && !this.dependentePK.equals(other.dependentePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Dependente[ dependentePK=" + dependentePK + " ]";
    }
    
}
