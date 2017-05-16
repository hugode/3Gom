/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BL.City;
import BL.Daily;
import BL.Today;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Aztech.Web
 */
public class WeatherRepository extends EntityManagerClass{
    
        //Mer te dhenat e motit per diten e sotme bazuar ne qytetin e perdoruesit dhe daten e sotme
        public Today getWeather(City c, Date d)throws  Exceptions{            
        Query query = em.createQuery("SELECT c FROM Today c WHERE c.cityId=:id AND"
                + " c.date=:date "
                + "ORDER BY c.id DESC").setMaxResults(1);
        query.setParameter("id", c);
        query.setParameter("date", d);

        try {
            
            return (Today) query.getSingleResult();
        } catch (NoResultException e) {
            throw new Exceptions("Nuk ka te ruajtuar te dhena per diten e sotme lokalisht");
        }
        }
        
        //Me te dhenat e motit per 5 ditet ne vijim duke u bazuar ne qytetin e perdoruesit dhe daten e sotme
        public List<Daily>  getDailyWeather(City c, Date d)throws  Exceptions{
        Query query = em.createQuery("SELECT d FROM Daily d WHERE d.cityId=:id AND"
                + " d.date>=:date "
                + "ORDER BY d.dailyId DESC").setMaxResults(5);
        query.setParameter("id", c);
        query.setParameter("date", d);
        
        try{
        return query.getResultList();
        }catch(NoResultException e){
            throw new Exceptions("Nuk ka te ruajtur te dhena per 5 ditet ne vijim lokalisht");
        }
        }
        
        
        //Ruaj te dhenat e motit per diten e sotme
        //Objekti Today() permban daten e sotme qytetin dhe te dhenat tjera
        public void setTodayWeather(Today t)throws Exceptions{
            try{
                em.getTransaction().begin();
                em.persist(t);
                em.getTransaction().commit();
            }catch(Exception e){
                throw new Exceptions("Probleme ne ruajtjen e motit lokalisht per diten e sotme"+e);
            }
        }
        
        //Ruaj te dhenat e motit per 5 ditet ne vijim
        //Objekti Daily() permban daten e sotme qytetin dhe te dhenat tjera
        public void setDailyWeather(Daily d)throws Exceptions{
            try{
                em.getTransaction().begin();
                em.persist(d);
                em.getTransaction().commit();
            }catch(Exception e){
                 throw new Exceptions("Problem me ruajtjen e motit lokalisht per 5 ditet ne vijim"+e);
            }
        }
        
        
        //Fshij lokalisht te dhenat e motit per diten e sotme bazuar ne qytetin e dhene
        public void clearTodayWeather(City c)throws Exceptions{
            try{
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Today c WHERE c.cityId=:city");
            query.setParameter("city", c);
            query.executeUpdate();
            em.getTransaction().commit();
            }catch(Exception e){
                System.out.println(e);
                throw new Exceptions(e.getMessage()+"--------------888888888888888");
            }
        }
        
        //Fshij lokalisht te dhenat e motit per 5 ditet ne vijim bazuar ne qytetin e dhene
        public void clearDailyWeather(City c)throws Exceptions{
            try{
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Daily d WHERE d.cityId=:city");
            query.setParameter("city", c);
            query.executeUpdate();
            em.getTransaction().commit();
            }catch(Exception e){
                System.out.println(e);
                throw new Exceptions(e.getMessage()+"--------------888888888888888");
            }
        }
        
}
