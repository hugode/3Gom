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
@Table(name = "daily")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Daily.findAll", query = "SELECT d FROM Daily d"),
    @NamedQuery(name = "Daily.findByDailyId", query = "SELECT d FROM Daily d WHERE d.dailyId = :dailyId"),
    @NamedQuery(name = "Daily.findByDate", query = "SELECT d FROM Daily d WHERE d.date = :date"),
    @NamedQuery(name = "Daily.findByDay", query = "SELECT d FROM Daily d WHERE d.day = :day"),
    @NamedQuery(name = "Daily.findByCond", query = "SELECT d FROM Daily d WHERE d.cond = :cond"),
    @NamedQuery(name = "Daily.findByMin", query = "SELECT d FROM Daily d WHERE d.min = :min"),
    @NamedQuery(name = "Daily.findByMax", query = "SELECT d FROM Daily d WHERE d.max = :max")})
public class Daily implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "daily_id")
    private Integer dailyId;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "day")
    private String day;
    @Basic(optional = false)
    @Column(name = "cond")
    private short cond;
    @Basic(optional = false)
    @Column(name = "min")
    private short min;
    @Basic(optional = false)
    @Column(name = "max")
    private short max;
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private City cityId;

    public Daily() {
    }

    public Daily(Integer dailyId) {
        this.dailyId = dailyId;
    }

    public Daily(Integer dailyId, Date date, String day, short cond, short min, short max) {
        this.dailyId = dailyId;
        this.date = date;
        this.day = day;
        this.cond = cond;
        this.min = min;
        this.max = max;
    }

    public Integer getDailyId() {
        return dailyId;
    }

    public void setDailyId(Integer dailyId) {
        this.dailyId = dailyId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public short getCond() {
        return cond;
    }

    public void setCond(short cond) {
        this.cond = cond;
    }

    public short getMin() {
        return min;
    }

    public void setMin(short min) {
        this.min = min;
    }

    public short getMax() {
        return max;
    }

    public void setMax(short max) {
        this.max = max;
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
        hash += (dailyId != null ? dailyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Daily)) {
            return false;
        }
        Daily other = (Daily) object;
        if ((this.dailyId == null && other.dailyId != null) || (this.dailyId != null && !this.dailyId.equals(other.dailyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BL.Daily[ dailyId=" + dailyId + " ]";
    }
    
}
