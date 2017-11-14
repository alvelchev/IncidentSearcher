/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adx_slevkov
 */
@Entity
@Table(name = "incidents")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Incident.findAll", query = "SELECT i FROM Incident i")
    , @NamedQuery(name = "Incident.findByIncId", query = "SELECT i FROM Incident i WHERE i.incId = :incId")
    , @NamedQuery(name = "Incident.findByIncNumber", query = "SELECT i FROM Incident i WHERE i.incNumber = :incNumber")
    , @NamedQuery(name = "Incident.findByIncDate", query = "SELECT i FROM Incident i WHERE i.incDate = :incDate")
    , @NamedQuery(name = "Incident.findByIncTitle", query = "SELECT i FROM Incident i WHERE i.incTitle = :incTitle")
    , @NamedQuery(name = "Incident.findByIncDescription", query = "SELECT i FROM Incident i WHERE i.incDescription = :incDescription")
    , @NamedQuery(name = "Incident.findByIncSolved", query = "SELECT i FROM Incident i WHERE i.incSolved = :incSolved")})
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "inc_id")
    private Integer incId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "inc_number")
    private String incNumber;
    @Column(name = "inc_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date incDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "inc_title")
    private String incTitle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 2147483647)
    @Column(name = "inc_description")
    private String incDescription;
    @Column(name = "inc_solved")
    private Boolean incSolved;
    @JoinColumn(name = "inc_cat", referencedColumnName = "cat_id")
    @ManyToOne
    private Category incCat;
    @JoinColumn(name = "inc_system", referencedColumnName = "system_id")
    @ManyToOne(optional = false)
    private System incSystem;

    public Incident() {
    }

    public Incident(Integer incId) {
        this.incId = incId;
    }

    public Incident(Integer incId, String incNumber, String incTitle, String incDescription) {
        this.incId = incId;
        this.incNumber = incNumber;
        this.incTitle = incTitle;
        this.incDescription = incDescription;
    }

    public Integer getIncId() {
        return incId;
    }

    public void setIncId(Integer incId) {
        this.incId = incId;
    }

    public String getIncNumber() {
        return incNumber;
    }

    public void setIncNumber(String incNumber) {
        this.incNumber = incNumber;
    }

    public Date getIncDate() {
        return incDate;
    }

    public void setIncDate(Date incDate) {
        this.incDate = incDate;
    }

    public String getIncTitle() {
        return incTitle;
    }

    public void setIncTitle(String incTitle) {
        this.incTitle = incTitle;
    }

    public String getIncDescription() {
        return incDescription;
    }

    public void setIncDescription(String incDescription) {
        this.incDescription = incDescription;
    }

    public Boolean getIncSolved() {
        return incSolved;
    }

    public void setIncSolved(Boolean incSolved) {
        this.incSolved = incSolved;
    }

    public Category getIncCat() {
        return incCat;
    }

    public void setIncCat(Category incCat) {
        this.incCat = incCat;
    }

    public System getIncSystem() {
        return incSystem;
    }

    public void setIncSystem(System incSystem) {
        this.incSystem = incSystem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (incId != null ? incId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Incident)) {
            return false;
        }
        Incident other = (Incident) object;
        if ((this.incId == null && other.incId != null) || (this.incId != null && !this.incId.equals(other.incId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return incTitle + ", " + incNumber;
    }

}
