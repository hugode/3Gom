/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BL.City;
import BL.Daily;
import BL.Reminders;
import BL.Users;
import GUI.Main;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RemindersRepository extends EntityManagerClass{
    final String URL_HOST = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20woeid%20%3D%22";             
    final String URL_FOOTER = "%22)%20and%20u%3D%22c%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    String URL;
    UsersRepository userRepo = new UsersRepository();
    WeatherRepository weatherRepo = new WeatherRepository();
    
    public List<Reminders> getListOfReminders(Users u) throws Exceptions, JSONException, ParseException{
        Date d = new Date();
        Query q = em.createQuery("SELECT r FROM Reminders r WHERE "
                + "r.remindersUser=:u AND "
                + "r.remindersDate>=:d "
                + "ORDER BY r.remindersDate ASC").setMaxResults(4);
        q.setParameter("u", u.getUsername());
        q.setParameter("d", d);
        try{
        List<Reminders> reminders = (List<Reminders>) q.getResultList();
        for(Reminders r : reminders)
            getRemindersWeatherOnline(r);
        return reminders;
        }catch(NoResultException e){
            throw new Exceptions("RemindersRepository => getListOfReminders: "+e.getMessage());
        }
    
    }
    
 
    /** 
     *  Mer qytetin nga reminders dhe kerko online per motin e ketij qyteti
     */
    private void getRemindersWeatherOnline(Reminders r) throws Exceptions, JSONException, ParseException{
        Calendar today = Calendar.getInstance();
        Calendar remindersDate = Calendar.getInstance();
        today.add(Calendar.DATE, 10);
        remindersDate.setTime(r.getRemindersDate());
        
        //Nese data e reminders nuk eshte brenda 10 ditesh, mos kerko online
        //per arsye se maksimumi i rezultatit nga Yahoo eshte per 10 ditet e ardhshme
        if(remindersDate.before(today))
            return;
        
        try{  
        City city = userRepo.getCity(r.getRemindersCity());
        JSONObject yahooWeather = getWeather(city.getZip());
        JSONArray dailyWeather = yahooWeather
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getJSONArray("forecast");
        weatherRepo.clearDailyWeather(city);
        for (int i = 0; i < dailyWeather.length(); i++) {
                    /***Moti ditor
                    * @param dailyDate Data e dites se dhene
                    * @param dailyDay Emri i dites se dhene
                    * @param dailyCondition Kushtet e motit per diten e dhen
                    * @param dailyMax Temperatura maksimale per diten e dhene
                    * @param dailyMin Temperatura minimale per diten e dhene
                    */
                    DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
                    Date dailyDate;
                    String dailyDay;
                    short dailyCondition, dailyMax, dailyMin; 
                    
                    
                //Mer te dhenat e motit nga vargu specifikisht per diten e dhene [i]
                // nese i=0, mer te dhenat e motit per diten e pare (sot)
                // nese i=1, mer te dhenat e motit per diten e dyte (neser)
                JSONObject getDailyWeatherByDay = (JSONObject) dailyWeather.get(i);
                
                //Mer daten nga JSON Objekti si String, dhe ktheje ne date
                dailyDate = df.parse(getDailyWeatherByDay.getString("date"));
                
                //Perkthe emrin e dites [Mon - E Hene]
                dailyDay = userRepo.setDay(getDailyWeatherByDay.getString("day"));
                dailyCondition = (short) getDailyWeatherByDay.getInt("code");
                dailyMax = (short) getDailyWeatherByDay.getInt("high");
                dailyMin = (short) getDailyWeatherByDay.getInt("low");
                //Ruaj te dhenat e motit lokalisht  
                weatherRepo.updateDailyWeatherInLocalhost(dailyDate, dailyDay, dailyCondition, dailyMax, dailyMin,city);
                
                }//FOR
        }catch(Exceptions e){
            System.out.println("RemindersRepository err: "+e.getMessage());
        }
    }
    private JSONObject getWeather(int cityId)throws Exceptions{
        try{
            URL = URL_HOST + cityId + URL_FOOTER;
            JSONObject yahooWeather = weatherRepo.getYahooWeather(URL);
            return yahooWeather;
        }catch(IOException | JSONException e){
            throw new Exceptions(e.getMessage());
        }
    }
    
    
    
    public boolean isWeatherFriendly(Reminders r) throws Exceptions{
        Query q = em.createQuery("SELECT d FROM Daily d WHERE "
                + "d.date=:d AND "
                + "d.cityId=:city AND "
                + "d.max>=:temp"
                );
        Date d = r.getRemindersDate();
        City city = new City(); 
        city.setId(r.getRemindersCity());
        q.setParameter("d", d);
        q.setParameter("city", city);
        q.setParameter("temp", r.getRemindersHigher());
        try{
           Daily daily  =(Daily) q.getSingleResult();
           if(daily.getDailyId()>0)
               return true;
        }catch(NoResultException e){
            return false;
        }
        return false;
    }
    
    public void addReminder(Reminders r) throws Exceptions{
        try{
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
        }catch(Exception e){
            throw new Exceptions(e.getMessage());
        }
    }
    
    public List<Reminders> getSharedReminders(Users u) throws Exceptions{
        Date d = new Date();
        Query q = em.createQuery("SELECT r FROM Reminders r WHERE "
                + "r.remindersUser!=:u AND "
                + "r.remindersDate>=:d AND "
                + "r.remindersShared=:sh "
                + "ORDER BY r.remindersDate ASC").setMaxResults(3);
        q.setParameter("u", u.getUsername());
        q.setParameter("d", d);
        q.setParameter("sh", true);
        try{
        List<Reminders> reminders = (List<Reminders>) q.getResultList();
        return reminders;
        }catch(NoResultException e){
           throw new Exceptions("RemindersRepository => Nuk ka shared remininders: "+e.getMessage());
        }
        
    }
    
}
