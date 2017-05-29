/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import BL.City;
import BL.Daily;
import BL.Reminders;
import BL.Today;
import BL.Users;
import DAL.Exceptions;
import DAL.UsersRepository;
import DAL.WeatherRepository;
import DAL.RemindersRepository;
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
    /**
     * @param useri Inicializimi i perdoruesit
     * @param city Inicializimi i City [qytetit], te dhenat <city> i mer nga <useri>
     * @param dailyList Inicializimi i listes me mot ditor [perdoret vetem ne rastet kur ka te ruajtur mot ditor lokalisht]
     * @param today Inicializimi i Today [moti aktual]
     * @param daily Inicializimi i Daily [moti ditor]
     * @param userRepo Inicializimi i grupit te metodave qe kontrollon informatat e perdoruesit <useri> [username,city_id]
     * @param weatherRepo Inicializimi i grupit te metodave qe kontrollon lidhjen me databaze ose server [yahoo.com]
     * @param URL_HOST Pjesa e pare e URL per lidhje me YahooWeather [per tu kompletuar si URL shtohet pjesa e dyte <line:169> dhe pjesa e trete <line:56>]
     * @param URL_FOOTER Pjesa e trete e URL per lidhje me YahooWeather, definon tipin e te dhenave [JSONObject] njesine matese [Celsious]
     * @param URL Bashkimi i URL_HOST + URL_CityID + URL_FOOTER [URL e plote per qasje ne YahooWeather <line:169>]
     */
    Users useri ;
    City city;
    List<Daily> dailyList;
    Today today = new Today();
    Daily daily = new Daily();
    UsersRepository userRepo = new UsersRepository();
    WeatherRepository weatherRepo = new WeatherRepository();
    final String URL_HOST = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20woeid%20%3D%22";             
    final String URL_FOOTER = "%22)%20and%20u%3D%22c%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    String URL;
    
            
    public static void main(String [] args) {            
        try{

           new Main().checkUser();

        }catch(Exception e){
            System.out.println("NOT ERR: "+e.getMessage());
        }
    }
 
    
    /*
    *METODAT:
    * 1)    Validimi i perdoruesit
    * 2)    Perkthim i emrit te dites
    * 3)    Pastrim i databazes
    * 4)    Marrja e te dhenave lokalisht
    * 5)    Marrja e te dhenave Online
    * 6)    Ruajtja e motit lokalisht
    * 7)    Shfaqja e te dhenave tek perdoruesi
    * 8)    Shfaqja e ikones
    * 9)    Ndryshimi i prapavise
    */
    
    /**1)    <Validimi i perdoruesit>   */
    //Kontrollo nese perdoruesi egziston 
    private void checkUser() throws Exception{           
      try{
                    
          useri = userRepo.getUser("doni","123456"); 
          city = useri.getCityId();
          //Nese perdoruesi ka te caktuar qytetin vazhdo me pjesen e motit 
          /*if(city.getId()>0)
            getWeatherLocaly();
          else
              throw new Exception("Perdoruesi nuk e ka te caktuar qytetin");*/
            

           RemindersRepository remindersRep = new RemindersRepository();
           List<Reminders> r = remindersRep.getListOfReminders(useri);
           System.out.println(r);
      }catch(Exception e){       
          throw new Exception("Main -> checkUser: "+e.getMessage());
      }
    }
    
    



    
    
    /**3)    <Pastrim i databazes>    */
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
    
    
    /**4)    <Marrja e te dhenave lokalisht>    */
    //Mer te dhenat e motit lokalisht
    private void getWeatherLocaly(){ 
        Date date = new Date();
        try {
        today = weatherRepo.getWeather(city,date);
        dailyList = weatherRepo.getDailyWeather(city,date);
        getWeatherOnline();
        } catch (Exceptions ex) {
         System.out.println("Get Weather Localy Error: "+ex);
        }
    }
    
    
    /**5)    <Marrja e te dhenave Online>   */ 
    //Mer te dhenat e motit nga YahooWeather dhe ruaj ato lokalisht
    private void getWeatherOnline(){ 
        //Bashko hostin e URL me ID te qytetit dhe ne fund shto edhe konfigurimet [moti te kthehet si JSON Object]
        URL = URL_HOST + city.getZip() + URL_FOOTER; 
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
         short currentCondition, currentTemp;
         
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
         * @alias Moti i sotem 
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
           currentTemp = (short) response
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
            currentDay = userRepo.setDay(currentDay.substring(0, 3));
            cDate = response
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getString("lastBuildDate");
            cDate = cDate.substring(5,16); 
            currentDate = df.parse(cDate);
            currentCondition = (short) response
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
                
                //Mer daten nga JSON Objekti si String, dhe ktheje ne date
                dailyDate = df.parse(getDailyWeatherByDay.getString("date"));
                
                //Perkthe emrin e dites [Mon - E Hene]
                dailyDay = userRepo.setDay(getDailyWeatherByDay.getString("day"));
                dailyCondition = (short) getDailyWeatherByDay.getInt("code");
                dailyMax = (short) getDailyWeatherByDay.getInt("high");
                dailyMin = (short) getDailyWeatherByDay.getInt("low");
                //Ruaj te dhenat e motit lokalisht  
                weatherRepo.updateDailyWeatherInLocalhost(dailyDate, dailyDay, dailyCondition, dailyMax, dailyMin,city);
                //Paraqit te dhenat e motit ditor tek perdoruesi ne baze te renditjes [(i=0) - pozita 1]
                displayDailyWeather(dailyDate,dailyDay,dailyCondition,dailyMax,dailyMin,i);
            }
            
            //Ruaj te dhenat e motit lokalisht
            weatherRepo.updateTodayWeatherInLocalhost(currentDay,
                                            currentDate,
                                            (short) currentCondition,
                                            (short) currentTemp,city);
            
            //Paraqit te dhenat e motit te sotem tek perdoruesi
            displayTodayWeather(currentTemp,currentWind,currentDay,currentCondition);
            
        }catch(JSONException | ParseException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    


    
    
    /**7)    <Shfaqja e te dhenave tek perdoruesi>    */
    private void displayTodayWeather(int currentTemp,String currentWind,String currentDay,int currentCondition){
        //Paraqit ikonen, dergo kushtet e motit dhe diten e sotme [0 - dita e sotme]
        setIcon(String.valueOf(currentCondition),0);
        
        System.out.println(
        "Temp: "+currentTemp+"\n"+
        "Wind: "+currentWind+"\n"+
        "Day: "+currentDay+"\n"+
        "Cond: "+currentCondition +"\n"       
                );
    }
    private void displayDailyWeather(Date dailyDate,String dailyDay,short dailyCondition,short dailyMax,short dailyMin,int i) {
        //Paraqit ikonen, dergo kushtet e motit dhe diten [(i=0) sot, (i=1) neser]
        setIcon(String.valueOf(dailyCondition),i);
        
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
    
    /**8)   <Shfaqja e ikonave>   */
    //Gjej ikonen bazuar ne kushtet e motit
    private void setIcon(String code, int i){
        String iconSource;
        short backgroundCode;
                if (code.matches("0|1|2")) {
                    iconSource = "mot_me_ere.ico";
                    backgroundCode = 0;
                } else if (code.matches("3|4|37|38|39")) {
                    iconSource = "vetetima.ico";
                    backgroundCode = 1;
                } else if (code.matches("5|6|7|25")) {
                    iconSource = "bore_me_shi.ico";
                    backgroundCode = 2;
                } else if (code.matches("9|10")) {
                    iconSource = "ngrice.ico";
                    backgroundCode = 3;
                } else if (code.matches("47|45|10|11|12|37|38|39")) {
                    iconSource = "rrebesh.ico";
                    backgroundCode = 4;
                } else if (code.matches("13|14|15|16|17|18|46|41|42|43")) {
                    iconSource = "bore.ico";
                    backgroundCode = 5;
                } else if (code.matches("19|20|21|22|23|24")) {
                    iconSource = "mjegull.ico";
                    backgroundCode = 6;
                } else if (code.matches("26|27|28|29|30|44")) {
                    iconSource = "mot_me_re.ico";
                    backgroundCode = 7;
                } else if (code.matches("31|32|33|34|36")) {
                    iconSource = "mot_me_diell.ico";
                    backgroundCode = 8;
                } else if (code.matches("35")) {
                    iconSource = "riga_lokale_shiu.ico";
                    backgroundCode = 9;
                } else {
                    iconSource = "erro_404.ico";
                    backgroundCode = 10;
                }
                if(i==0) setBackground(backgroundCode);
                displayIcon(iconSource, i);
    }
    //Paraqit ikonen e dhene [iconSource] tek pozicioni i caktuar 
    //  [(i=0) Moti aktuar, (i=1,2,3) Moti ditor]
    private void displayIcon(String iconSource, int i){
        switch(i){
            case 0:
                //icon0.setIcon(iconSource)
                break;
            case 1:
                //icon1.setIcon(iconSource)
                break; 
            case 2:
                //icon2.setIcon(iconSource)
                break;
            case 3:
                //icon3.setIcon(iconSource)
                break;
            case 4:
                //icon4.setIcon(iconSource)
                break;
            case 5:
                //icon5.setIcon(iconSource)
                break;
            default:
                break; 
        }
    }
    
    
    /**9)   <Ndryshimi i prapavise>   */
    //Ndrysho prapavine bazuar ne kushtet e motit [(conditionCode=1)]
    private void setBackground(short conditionCode){
        String imageSource; 
        
        switch(conditionCode){ 
            case 0:
                imageSource = "mot_me_ere.jpg";
                break;
            case 1:
                imageSource = "vetetima.jpg";
                break;
            case 2:
                imageSource = "bore_me_shi.jpg";
                break;
            case 3:
                imageSource = "ngrice.jpg";
                break;
            case 4:
                imageSource = "rrebesh.jpg";
                break;
            case 5:
                imageSource = "bore.jpg";
                break;
            case 6:
                imageSource = "mjegull.jpg";
                break;
            case 7:
                imageSource = "mot_me_re.jpg";
                break;
            case 8:
                imageSource = "mot_me_diell.jpg";
                break;
            case 9:
                imageSource = "riga_lokale_shiu.jpg";
                break;
            default:
                imageSource = "error_404.jpg";
                break;
        }
        //backgroundImgae.setImage(imageSource);
    }
    
    
    
    
    /**10) <Set Rem >*/
    public void setRem(Reminders r, int isset){
        if(isset==1)
            System.out.println(r.getRemindersTitle());
    }
}
