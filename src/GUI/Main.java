/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import BL.City;
import BL.Daily;
import BL.Today;
import BL.Users;
import DAL.Exceptions;
import DAL.UsersRepository;
import DAL.WeatherRepository;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author Aztech.Web
 */
public class Main {
    Users useri ;
    City city;
    List<Daily> dailyList;
    Today today = new Today();
    Daily daily = new Daily();
    WeatherRepository weatherRepo = new WeatherRepository();
    Date date = new Date();
    short cond = 1, max = 16, min = 8;
    String day = "E Marte";
    String URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20woeid%3D%22";
                       
    
    //Mer te dhenat e motit lokalisht
    private void getWeatherLocaly(){ 
        try {
        today = weatherRepo.getWeather(city,date);
        dailyList = weatherRepo.getDailyWeather(city,date);
        clearCache();
        getWeatherOnline();
        } catch (Exceptions ex) {
         System.out.println("Get Weather Localy Error: "+ex);
        }
    }
    

    
    
    //Fshij te gjitha te dhenat e motit te ruajtura lokalisht per qytetin e perdoruesit
    private void clearCache(){
        try {
        //Fshij te dhenat per javen e ardhshme ne databazen lokale (nese ka), dhe shto te rejat
        weatherRepo.clearDailyWeather(city);
        
        //Fshij te dhenat per diten e sotme ne databazen lokale (nese ka), dhe shto te rejat
        weatherRepo.clearTodayWeather(city);
        
        } catch (Exceptions ex) {
             System.out.println("Clear Cache: "+ex);
        }
    }
    
    
    
    
    //Mer te dhenat e motit nga YahooWeather dhe ruaj ato lokalisht
    private void getWeatherOnline(){ 
        try{
            //weatherRepo.getYahooWeather(URL);
        }catch(Exception e){
            System.out.println(e+"-----88888888888----------");
        }
        
        
        
    //nese te dhenat online jane te gatshem vazhdo me shtimin e te dhenave online 
    updateTodayWeatherInLocalhost(day,date,cond,max,min);
    for(int i=0;i<5;i++)
            updateDailyWeatherInLocalhost(day,date,cond,max,min);
    }

    
   //Kontrollo nese perdoruesi egziston 
    private void checkUser() throws Exception{           
      try{
          UsersRepository repo = new UsersRepository();          
          useri = repo.getUser("doni","123456"); 
          city = useri.getCityId();
          city.setZip(53615);
          setZipOfCity();
          //Nese perdoruesi ka te caktuar qytetin vazhdo me pjesen e motit 
          if(city.getId()>0)
            getWeatherLocaly();
          else
              throw new Exception("Perdoruesi nuk e ka te caktuar qytetin");
      }catch(Exception e){       
          throw new Exception(e);
      }
    }
    private void setZipOfCity(){
        URL += city.getZip()+
        "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    }
    
    
    public static void main(String [] args) {            
        try{
            new Main().checkUser();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    
    
    
    
    
    /**
     * Ruajtja e motit lokalisht
     */
    
    //Shto ne databazen lokale te dhenat e motit per diten e sotme
    private void updateTodayWeatherInLocalhost(String day, Date date, short cond, short max, short min){
        try{           
            Today today = new Today();
            today.setCityId(city);
            today.setDay(day);
            today.setDate(date);
            today.setCond(cond);
            today.setMax(max);
            today.setMin(min);
            weatherRepo.setTodayWeather(today); 
        }catch(Exception ex){
             System.out.println("Problem gjat ruajtjes te motit ditor lokalisht: "+ex);
        } 
    }
    
    
    //Shto ne databazen lokale te dhenat e motit per ditet ne vijim
    private void updateDailyWeatherInLocalhost(String day,Date date,  short cond, short min, short max){    
        try{    
            Daily daily = new Daily();
            daily.setCityId(city);
            daily.setDate(date);
            daily.setDay(day);
            daily.setCond(cond);
            daily.setMin(min);
            daily.setMax(max); 
            weatherRepo.setDailyWeather(daily);            
        }catch(Exception ex){
            System.out.println("Problem gjat ruajtjes te motit 5 ditor lokalisht: "+ex);
        }
    }
    
    
    
    
    
    /**
     *Funksionet per kontrollimin e stringjeve dhe te tjera
     */
   private boolean hasSpaces(String s){
    //kontrollo per hapsira    
    Pattern pattern = Pattern.compile("\\s");
    Matcher matcher = pattern.matcher(s);
    return  matcher.find();
    }
    
    private boolean hasSpecialChars(String s){
    //kontrollo per cdo karakter tjeter perveq A-Z, 0-9 dhe . (pika)
        //nuk ka rendesi nese karakteret jane shkronja te medha ose te vogla (CASE_INSENSITIVE)
    Pattern p = Pattern.compile("[^a-z0-9. ]", Pattern.CASE_INSENSITIVE);
    Matcher m = p.matcher(s);
    return  m.find();
    } 
    
}
