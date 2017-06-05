/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BL.City;
import BL.Daily;
import BL.Today;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.json.*;
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
            System.out.println("Nuk ka te ruajtuar te dhena per diten e sotme lokalisht");
        }
        return null;
        }
        
        //Me te dhenat e motit per 5 ditet ne vijim duke u bazuar ne qytetin e perdoruesit dhe daten e sotme
        public List<Daily>  getDailyWeather(City c, Date d)throws  Exceptions{
        Query query = em.createQuery("SELECT d FROM Daily d WHERE d.cityId=:id AND"
                + " d.date>=:date "
                + "ORDER BY d.dailyId ").setMaxResults(6);
        query.setParameter("id", c);
        query.setParameter("date", d);
        
        try{
        return query.getResultList();
        }catch(NoResultException e){
            System.out.println("Nuk ka te ruajtur te dhena per 5 ditet ne vijim lokalisht");
        }
        return null;
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
                throw new Exceptions("Problem me fshirjen e motit te sotem lokalisht: "+e.getMessage());
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
                throw new Exceptions("Problem me fshirjen e motit ditor lokalisht: "+e.getMessage());
            }
        }
        
        
        /*
        *Mer motin Online bazuar ne URL
        */
        //Kthe JSON Objektin nga URL e dhene
        public JSONObject getYahooWeather(String url) throws IOException, JSONException {
            //Lexo te dhenat qe paraqiten ne URL e dhene dhe ruaj si InputStream objekt
            InputStream is = new URL(url).openStream();
            try {
                //Lexo tekstin nga InputStream, dhe ktheje ate ne BufferedReader
                //ne menyre qe te lexohen karakteret , vargjet(arrays) dhe rreshtat ne forme sa me efikase
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                
                //Stringut jsonText vendosja te gjithe rreshtat qe ka BufferedReader 'rd'
                //perdoret metoda readAll() <line 148>
                String jsonText = readAll(rd);
                
                //Krijo nje JSONObject nga Stringu i dhene
                JSONObject json = new JSONObject(jsonText);
                return json;
            } finally {
                //Mbyll InputStream
                is.close();
            }
        }
        //Kthe BufferedReader ne String
        private static String readAll(Reader rd) throws IOException {
            
            StringBuilder sb = new StringBuilder();
            int cp;
            
            //Perderisa ka karaktere per lexim vazhdo shtimin ne StringBuilder te rreshtit
            // ne momentin kur nuk ka me shume karaktere, del nga while loop sepse paraqet -1
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            //Kthe karakteret si String
            return sb.toString();
        }
        
        
    //Shto ne databazen lokale te dhenat e motit per diten e sotme  
    public void updateTodayWeatherInLocalhost(String day, Date date, short cond, short current, City city){
        try{           
            Today updateToday = new Today();
            updateToday.setCityId(city);
            updateToday.setDay(day);
            updateToday.setDate(date);
            updateToday.setCond(cond);
            updateToday.setCurrent(current);
            setTodayWeather(updateToday); 
        }catch(Exception ex){
             System.out.println("Problem gjat ruajtjes te motit ditor lokalisht: "+ex);
        } 
    }
    //Shto ne databazen lokale te dhenat e motit per ditet ne vijim
    public void updateDailyWeatherInLocalhost(Date date, String day,short cond, short max, short min, City city){    
        try{    
            
            Daily dailyThisDay = new Daily();
            dailyThisDay.setCityId(city);
            dailyThisDay.setDate(date);
            dailyThisDay.setDay(day);
            dailyThisDay.setCond(cond);
            dailyThisDay.setMin(min);
            dailyThisDay.setMax(max); 
            setDailyWeather(dailyThisDay); 
        }catch(Exception ex){
            System.out.println("Problem gjat ruajtjes te motit 5 ditor lokalisht: "+ex);
        }
    }
    
    
        
        
}
