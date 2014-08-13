/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rodolfo
 */
@Entity
@Table(name = "empregado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empregado.findAll", query = "SELECT e FROM Empregado e"),
    @NamedQuery(name = "Empregado.findByNome", query = "SELECT e FROM Empregado e WHERE e.nome = :nome"),
    @NamedQuery(name = "Empregado.findByCpf", query = "SELECT e FROM Empregado e WHERE e.cpf = :cpf"),
    @NamedQuery(name = "Empregado.findByDatanasc", query = "SELECT e FROM Empregado e WHERE e.datanasc = :datanasc"),
    @NamedQuery(name = "Empregado.findByEndereco", query = "SELECT e FROM Empregado e WHERE e.endereco = :endereco"),
    @NamedQuery(name = "Empregado.findBySexo", query = "SELECT e FROM Empregado e WHERE e.sexo = :sexo")})
public class Empregado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Id
    @Basic(optional = false)
    @Column(name = "CPF")
    private String cpf;
    @Column(name = "DATANASC")
    @Temporal(TemporalType.DATE)
    private Date datanasc;
    @Column(name = "ENDERECO")
    private String endereco;
    @Column(name = "SEXO")
    private Character sexo;
    @JoinColumn(name = "NDEP", referencedColumnName = "NUMERO")
    @ManyToOne(optional = false)
    private Departamento ndep;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empregado")
    private Collection<Dependente> dependenteCollection;

    public Empregado() {
    }

    public Empregado(String cpf) {
        this.cpf = cpf;
    }

    public Empregado(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(Date datanasc) {
        this.datanasc = datanasc;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Departamento getNdep() {
        return ndep;
    }

    public void setNdep(Departamento ndep) {
        this.ndep = ndep;
    }

    @XmlTransient
    public Collection<Dependente> getDependenteCollection() {
        return dependenteCollection;
    }

    public void setDependenteCollection(Collection<Dependente> dependenteCollection) {
        this.dependenteCollection = dependenteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpf != null ? cpf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empregado)) {
            return false;
        }
        Empregado other = (Empregado) object;
        if ((this.cpf == null && other.cpf != null) || (this.cpf != null && !this.cpf.equals(other.cpf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Empregado[ cpf=" + cpf + " ]";
    }
    
}
