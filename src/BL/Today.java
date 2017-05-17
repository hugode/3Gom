/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aztech.Web
 */
@Entity
@Table(name = "today")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Today.findAll", query = "SELECT t FROM Today t"),
    @NamedQuery(name = "Today.findById", query = "SELECT t FROM Today t WHERE t.id = :id"),
    @NamedQuery(name = "Today.findByDay", query = "SELECT t FROM Today t WHERE t.day = :day"),
    @NamedQuery(name = "Today.findByDate", query = "SELECT t FROM Today t WHERE t.date = :date"),
    @NamedQuery(name = "Today.findByCond", query = "SELECT t FROM Today t WHERE t.cond = :cond"),
    @NamedQuery(name = "Today.findByCurrent", query = "SELECT t FROM Today t WHERE t.current = :current")})
public class Today implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "day")
    private String day;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "cond")
    private Short cond;
    @Column(name = "current")
    private Short current;
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ManyToOne
    private City cityId;

    public Today() {
    }

    public Today(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Short getCond() {
        return cond;
    }

    public void setCond(Short cond) {
        this.cond = cond;
    }

    public Short getCurrent() {
        return current;
    }

    public void setCurrent(Short current) {
        this.current = current;
    }

    public City getCityId() {
        return cityId;
    }

    public void setCityId(City cityId) {
        this.cityId = cityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Today)) {
            return false;
        }
        Today other = (Today) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BL.Today[ id=" + id + " ]";
    }
    
}
