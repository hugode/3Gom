/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BL.City;
import BL.Reminders;
import BL.Users;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    
    public List<Reminders> getListOfReminders(Users u) throws Exceptions{
        Date d = new Date();
        Query q = em.createQuery("SELECT r FROM Reminders r WHERE "
                + "r.remindersUser=:u AND "
                + "r.remindersDate>=:d "
                + "ORDER BY r.remindersDate ASC").setMaxResults(10);
        q.setParameter("u", u.getUsersPK().getUsername());
        q.setParameter("d", d);
        try{
        List<Reminders> reminders = (List<Reminders>) q.getResultList();
        return reminders;
        }catch(NoResultException e){
            System.out.println("Nuk ka result.");
        }catch(Exception e){
            throw new Exceptions("RemindersRepository -> getListOfReminders: "+e.getMessage());
        }
        return null;
    }
    
    public List<Reminders> getReminders(Users u) throws Exceptions, JSONException, ParseException  {
        List<Reminders> allReminders = getListOfReminders(u);
        try{
            for(Reminders reminder : allReminders){
             if(reminder.getRemindersIsset()==1){
                City city = userRepo.getCity(reminder.getRemindersCity());
                URL = URL_HOST + city.getZip() + URL_FOOTER;
                
      
                JSONObject yahooWeather = getWeather(URL);
                


                JSONArray dailyWeather = yahooWeather
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getJSONArray("forecast");
              
                            
                weatherRepo.clearDailyWeather(city);
                
                for (int i = 0; i < 5; i++) {
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
             }//IF
            }//FOR
        }catch(Exceptions e){
           throw new Exceptions(e.getMessage());       
        }
        
        return null;
    }
    private JSONObject getWeather(String URL)throws Exceptions{
        try{
            JSONObject yahooWeather = weatherRepo.getYahooWeather(URL);
            return yahooWeather;
        }catch(IOException | JSONException e){
            throw new Exceptions(e.getMessage());
        }
    }
    
}
