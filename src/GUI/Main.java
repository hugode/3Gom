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
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
    short cond = 1,temp = 10, max = 16, min = 8;
    String day = "E Marte";
    String URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22pristina%2Crs%22)%20%20and%20u%3D%22c%22&format=json&diagnostics=true&env=store%3A%2F%2Falltableswithkeys";
                 
    
    public static void main(String [] args) {            
        try{
            new Main().checkUser();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
 
    
    /*
    *
    *METODAT PER 
    * 1)    Validimin e perdoruesit
    * 2)    Perkthim 
    * 3)    Pastrim i databazes
    * 4)    Marrja e te dhenave lokalisht
    * 5)    Marrja e te dhenave Online
    * 6)    Ruajtja e motit lokalisht
    * 7)    Shfaqja e te dhenave tek perdoruesi
    */
    
    //1)
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
    //Kontrollo nese perdoruesi egziston 
    private void checkUser() throws Exception{           
      try{
          UsersRepository repo = new UsersRepository();          
          useri = repo.getUser("doni","123456"); 
          city = useri.getCityId();
          //Nese perdoruesi ka te caktuar qytetin vazhdo me pjesen e motit 
          if(city.getId()>0)
            getWeatherLocaly();
          else
              throw new Exception("Perdoruesi nuk e ka te caktuar qytetin");
      }catch(Exception e){       
          throw new Exception(e);
      }
    }
    
    
    //2)
    //Perkthe emrin e dites
    private static String setDay(String Day){      
        if (Day.equals("Mon")) 
                return "E Hene";
         else if (Day.equals("Tue")) 
                return "E Marte";
         else if (Day.equals("Wed")) 
                return "E Merkure";
         else if (Day.equals("Thu")) 
                return "E Enjete";
         else if (Day.equals("Fri")) 
                return "E Premte";
         else if (Day.equals("Sat")) 
                return "E Shtunë";
         else if (Day.equals("Sun")) 
                return "E Diel";
        return null;        
    }
    
    
    //3)
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
    
    
    //4)
    //Mer te dhenat e motit lokalisht
    private void getWeatherLocaly(){ 
        try {
        today = weatherRepo.getWeather(city,date);
        dailyList = weatherRepo.getDailyWeather(city,date);
        getWeatherOnline();
        } catch (Exceptions ex) {
         System.out.println("Get Weather Localy Error: "+ex);
        }
    }
    
    //5) 
    //Mer te dhenat e motit nga YahooWeather dhe ruaj ato lokalisht
    private void getWeatherOnline(){ 
        try{
            JSONObject yahooWeather = weatherRepo.getYahooWeather(URL);
            parseJsonFeed(yahooWeather);
        }catch(IOException | JSONException e){
            System.out.println("Get Weather Online Error: "+e);
        }
    }
    //Kthe nga JSON Objekt ne te dhena te gatshme per paraqitje dhe ruajtje
    private void parseJsonFeed(JSONObject response) {
        //Pastro databazen lokale nga te dhenat e motit
        clearCache();
         /***Moti i sotem
          * @param df Formati i dates e cila do kthehet nga String ne Date 
          * @param currentWind Shpejtesia e eres per momentin
          * @param currentDay Emri i dites se sotme 
          * @param cDate String qe mbane daten e sotme per tu konvertuar ne Date() currentDate
          * @param currentDate Data e sotme
          * @param currentCondition Kushtet e motit per momentin [(0-47) kushtet aktuale, (3200) not available]
          * 
          */  
         DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
         String currentWind, currentDay , cDate;
         Date currentDate;
         int currentCondition, currentTemp;
         
         /***Moti ditor
          * @param dailyDate Data e dites se dhene
          * @param dailyDay Emri i dites se dhene
          * @param dailyCondition Kushtet e motit per diten e dhen
          * @param dailyMax Temperatura maksimale per diten e dhene
          * @param dailyMin Temperatura minimale per diten e dhene
          */
         Date dailyDate;
         String dailyDay;
         short dailyCondition, dailyMax, dailyMin;
        /**
         * @alias Today 
         * 
         * Perditeso motin momental
         * @param currentTemp       =   temperatura momentale
         * @param currentWind       =   shpejtesia e eres per momentin
         * @param currentDay        =   emri i dites se sotme
         * @param currentCondition  =   kushtet atmosferike
         */ 
        try {
            /***
             * Moti i sotem
             */
           currentTemp = response
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getJSONObject("condition")
                    .getInt("temp");
            currentWind = response
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("wind")
                    .getString("speed");                    
            currentDay = response
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getString("lastBuildDate");
            currentDay = setDay(currentDay.substring(0, 3));
            cDate = response
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getString("lastBuildDate");
            cDate = cDate.substring(5,16); 
            currentDate = df.parse(cDate);
            currentCondition = response
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getJSONObject("condition")
                    .getInt("code");   
            //Deklaro vargun dailyWeather i cili permbane motin per 5 ditet ne vijim
            JSONArray dailyWeather = response
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getJSONArray("forecast");
            
            //mer 5 elementet e para te vargut [5 ditet e motit] 
            for (int i = 0; i < 5; i++) {
                //Mer te dhenat e motit nga vargu specifikisht per diten e dhene [i]
                // nese i=0, mer te dhenat e motit per diten e pare (sot)
                // nese i=1, mer te dhenat e motit per diten e dyte (neser)
                JSONObject getDailyWeatherByDay = (JSONObject) dailyWeather.get(i);

                dailyDate = df.parse(getDailyWeatherByDay.getString("date"));
                dailyDay = setDay(getDailyWeatherByDay.getString("day"));
                dailyCondition = (short) getDailyWeatherByDay.getInt("code");
                dailyMax = (short) getDailyWeatherByDay.getInt("high");
                dailyMin = (short) getDailyWeatherByDay.getInt("low");
                  
                updateDailyWeatherInLocalhost(dailyDate, dailyDay, dailyCondition, dailyMax, dailyMin);
                setDailyWeather(dailyDate,dailyDay,dailyCondition,dailyMax,dailyMin,i);
            }
            
            //Ruaj te dhenat e motit lokalisht
            updateTodayWeatherInLocalhost(currentDay,
                                            currentDate,
                                            (short) currentCondition,
                                            (short) currentTemp);
            
        }catch(Exception e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
    //6)
    //Shto ne databazen lokale te dhenat e motit per diten e sotme
    private void updateTodayWeatherInLocalhost(String day, Date date, short cond, short current){
        try{           
            Today today = new Today();
            today.setCityId(city);
            today.setDay(day);
            today.setDate(date);
            today.setCond(cond);
            today.setCurrent(current);
            weatherRepo.setTodayWeather(today); 
        }catch(Exception ex){
             System.out.println("Problem gjat ruajtjes te motit ditor lokalisht: "+ex);
        } 
    }
    //Shto ne databazen lokale te dhenat e motit per ditet ne vijim
    private void updateDailyWeatherInLocalhost(Date date, String day,short cond, short max, short min){    
        try{    
            Daily dailyThisDay = new Daily();
            dailyThisDay.setCityId(city);
            dailyThisDay.setDate(date);
            dailyThisDay.setDay(day);
            dailyThisDay.setCond(cond);
            dailyThisDay.setMin(min);
            dailyThisDay.setMax(max); 
            weatherRepo.setDailyWeather(dailyThisDay); 
        }catch(Exception ex){
            System.out.println("Problem gjat ruajtjes te motit 5 ditor lokalisht: "+ex);
        }
    }
    
    
    //7)
    private void setDailyWeather(Date dailyDate,String dailyDay,short dailyCondition,short dailyMax,short dailyMin,int i) {
            switch (i){
                case 0:
                    /*day0.setText(Day);
                    day0Min.setText(feedObj.getString("low"));
                    day0Max.setText(feedObj.getString("high"));
                    setIcon(i, code);*/
                    break;
                case 1:
                    /*day1.setText(Day);
                    day1Min.setText(feedObj.getString("low"));
                    day1Max.setText(feedObj.getString("high"));
                    setIcon(i, code);*/
                    break;
                case 2:
                    /* day2.setText(Day);
                    day2Min.setText(feedObj.getString("low"));
                    day2Max.setText(feedObj.getString("high"));
                    setIcon(i, code);*/
                    break;
                case 3:
                    /*day3.setText(Day);
                    day3Min.setText(feedObj.getString("low"));
                    day3Max.setText(feedObj.getString("high"));
                    setIcon(i, code);*/
                    break;
                case 4:
                    /* day4.setText(Day);
                    day4Min.setText(feedObj.getString("low"));
                    day4Max.setText(feedObj.getString("high"));
                    setIcon(i, code);/*/
                    break;
                default:
                    break;
        }
    }
}
