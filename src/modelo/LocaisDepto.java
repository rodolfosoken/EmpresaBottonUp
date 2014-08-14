/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rodolfo
 */
@Entity
@Table(name = "locais_depto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LocaisDepto.findAll", query = "SELECT l FROM LocaisDepto l"),
    @NamedQuery(name = "LocaisDepto.findByNumero", query = "SELECT l FROM LocaisDepto l WHERE l.locaisDeptoPK.numero = :numero"),
    @NamedQuery(name = "LocaisDepto.findByLocal", query = "SELECT l FROM LocaisDepto l WHERE l.locaisDeptoPK.local = :local")})
public class LocaisDepto implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LocaisDeptoPK locaisDeptoPK;
    @JoinColumn(name = "numero", referencedColumnName = "NUMERO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Departamento departamento;

    public LocaisDepto() {
    }

    public LocaisDepto(LocaisDeptoPK locaisDeptoPK) {
        this.locaisDeptoPK = locaisDeptoPK;
    }

    public LocaisDepto(int numero, String local) {
        this.locaisDeptoPK = new LocaisDeptoPK(numero, local);
    }

    public LocaisDeptoPK getLocaisDeptoPK() {
        return locaisDeptoPK;
    }

    public void setLocaisDeptoPK(LocaisDeptoPK locaisDeptoPK) {
        this.locaisDeptoPK = locaisDeptoPK;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locaisDeptoPK != null ? locaisDeptoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LocaisDepto)) {
            return false;
        }
        LocaisDepto other = (LocaisDepto) object;
        if ((this.locaisDeptoPK == null && other.locaisDeptoPK != null) || (this.locaisDeptoPK != null && !this.locaisDeptoPK.equals(other.locaisDeptoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.LocaisDepto[ locaisDeptoPK=" + locaisDeptoPK + " ]";
    }
    
}
