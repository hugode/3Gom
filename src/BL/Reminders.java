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
import javax.persistence.Lob;
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
@Table(name = "reminders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reminders.findAll", query = "SELECT r FROM Reminders r"),
    @NamedQuery(name = "Reminders.findByRemindersId", query = "SELECT r FROM Reminders r WHERE r.remindersId = :remindersId"),
    @NamedQuery(name = "Reminders.findByRemindersUser", query = "SELECT r FROM Reminders r WHERE r.remindersUser = :remindersUser"),
    @NamedQuery(name = "Reminders.findByRemindersTitle", query = "SELECT r FROM Reminders r WHERE r.remindersTitle = :remindersTitle"),
    @NamedQuery(name = "Reminders.findByRemindersDate", query = "SELECT r FROM Reminders r WHERE r.remindersDate = :remindersDate"),
    @NamedQuery(name = "Reminders.findByRemindersHigher", query = "SELECT r FROM Reminders r WHERE r.remindersHigher = :remindersHigher"),
    @NamedQuery(name = "Reminders.findByRemindersCity", query = "SELECT r FROM Reminders r WHERE r.remindersCity = :remindersCity"),
    @NamedQuery(name = "Reminders.findByRemindersIsset", query = "SELECT r FROM Reminders r WHERE r.remindersIsset = :remindersIsset")})
public class Reminders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "remindersId")
    private Integer remindersId;
    @Basic(optional = false)
    @Column(name = "reminders_user")
    private String remindersUser;
    @Column(name = "reminders_title")
    private String remindersTitle;
    @Lob
    @Column(name = "reminders_description")
    private String remindersDescription;
    @Column(name = "reminders_date")
    @Temporal(TemporalType.DATE)
    private Date remindersDate;
    @Column(name = "reminders_higher")
    private Short remindersHigher;
    @Column(name = "reminders_city")
    private Integer remindersCity;
    @Column(name = "reminders_isset")
    private Short remindersIsset;

    public Reminders() {
    }

    public Reminders(Integer remindersId) {
        this.remindersId = remindersId;
    }

    public Reminders(Integer remindersId, String remindersUser) {
        this.remindersId = remindersId;
        this.remindersUser = remindersUser;
    }

    public Integer getRemindersId() {
        return remindersId;
    }

    public void setRemindersId(Integer remindersId) {
        this.remindersId = remindersId;
    }

    public String getRemindersUser() {
        return remindersUser;
    }

    public void setRemindersUser(String remindersUser) {
        this.remindersUser = remindersUser;
    }

    public String getRemindersTitle() {
        return remindersTitle;
    }

    public void setRemindersTitle(String remindersTitle) {
        this.remindersTitle = remindersTitle;
    }

    public String getRemindersDescription() {
        return remindersDescription;
    }

    public void setRemindersDescription(String remindersDescription) {
        this.remindersDescription = remindersDescription;
    }

    public Date getRemindersDate() {
        return remindersDate;
    }

    public void setRemindersDate(Date remindersDate) {
        this.remindersDate = remindersDate;
    }

    public Short getRemindersHigher() {
        return remindersHigher;
    }

    public void setRemindersHigher(Short remindersHigher) {
        this.remindersHigher = remindersHigher;
    }

    public Integer getRemindersCity() {
        return remindersCity;
    }

    public void setRemindersCity(Integer remindersCity) {
        this.remindersCity = remindersCity;
    }

    public Short getRemindersIsset() {
        return remindersIsset;
    }

    public void setRemindersIsset(Short remindersIsset) {
        this.remindersIsset = remindersIsset;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (remindersId != null ? remindersId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reminders)) {
            return false;
        }
        Reminders other = (Reminders) object;
        if ((this.remindersId == null && other.remindersId != null) || (this.remindersId != null && !this.remindersId.equals(other.remindersId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BL.Reminders[ remindersId=" + remindersId + " ]";
    }
    
}
