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
public class LocaisDeptoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "numero")
    private int numero;
    @Basic(optional = false)
    @Column(name = "LOCAL")
    private String local;

    public LocaisDeptoPK() {
    }

    public LocaisDeptoPK(int numero, String local) {
        this.numero = numero;
        this.local = local;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) numero;
        hash += (local != null ? local.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LocaisDeptoPK)) {
            return false;
        }
        LocaisDeptoPK other = (LocaisDeptoPK) object;
        if (this.numero != other.numero) {
            return false;
        }
        if ((this.local == null && other.local != null) || (this.local != null && !this.local.equals(other.local))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.LocaisDeptoPK[ numero=" + numero + ", local=" + local + " ]";
    }
    
}
