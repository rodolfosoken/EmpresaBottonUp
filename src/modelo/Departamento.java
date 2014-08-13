/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rodolfo
 */
@Entity
@Table(name = "departamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d"),
    @NamedQuery(name = "Departamento.findByNome", query = "SELECT d FROM Departamento d WHERE d.nome = :nome"),
    @NamedQuery(name = "Departamento.findByNumero", query = "SELECT d FROM Departamento d WHERE d.numero = :numero")})
public class Departamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Id
    @Basic(optional = false)
    @Column(name = "NUMERO")
    private Integer numero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ndep")
    private Collection<Empregado> empregadoCollection;

    public Departamento() {
    }

    public Departamento(Integer numero) {
        this.numero = numero;
    }

    public Departamento(Integer numero, String nome) {
        this.numero = numero;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @XmlTransient
    public Collection<Empregado> getEmpregadoCollection() {
        return empregadoCollection;
    }

    public void setEmpregadoCollection(Collection<Empregado> empregadoCollection) {
        this.empregadoCollection = empregadoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numero != null ? numero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Departamento[ numero=" + numero + " ]";
    }
    
}
