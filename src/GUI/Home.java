package GUI;

import java.awt.Color;
import BL.City;
import BL.Daily;
import BL.Reminders;
import BL.Today;
import BL.Users;
import DAL.Exceptions;
import DAL.UsersRepository;
import DAL.WeatherRepository;
import DAL.RemindersRepository;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.converter.DateStringConverter;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author Ardian
 */
public class Home extends javax.swing.JFrame {

    
    
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
    Users useri = null;
    City city;
    List<Daily> dailyList;
    Today today = new Today();
    Daily daily = new Daily();
    UsersRepository userRepo = new UsersRepository();
    WeatherRepository weatherRepo = new WeatherRepository();
    RemindersRepository remindRepo = new RemindersRepository();
    final String URL_HOST = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20woeid%20%3D%22";             
    final String URL_FOOTER = "%22)%20and%20u%3D%22c%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    String URL;
    
    
    /*
    *METODAT:
    * 1)    Validimi i perdoruesit
    * 2)    Mer reminders
    * 3)    Pastrim i databazes
    * 4)    Marrja e te dhenave lokalisht
    * 5)    Marrja e te dhenave Online
    * 6)    Ruajtja e motit lokalisht
    * 7)    Shfaqja e te dhenave tek perdoruesi
    * 8)    Shfaqja e ikones
    * 9)    Ndryshimi i prapavise
    */
    public Home() {
        initComponents();
        this.setLocationRelativeTo(null);
        
            
        
        ekspresPanel.setOpaque(false);
        telegrafPanel.setOpaque(false);
        lajminetPanel.setOpaque(false);
        
        kosovapressPanel.setOpaque(false);
        share0panel.setOpaque(false);
        share1panel.setOpaque(false);
        
        remind0.setOpaque(false);
        remind1.setOpaque(false);
        remind2.setOpaque(false);
        remind3.setOpaque(false);
        share3panel.setOpaque(false);
        
        remind0.setVisible(false);
        remind1.setVisible(false);
        remind2.setVisible(false);
        remind3.setVisible(false);
        

            

         
    }
    
    
    
    private void gotoLogin(){
        System.exit(0);
    }
    /**1)    <Validimi i perdoruesit>   */
    //Kontrollo nese perdoruesi egziston 
    public void checkUser(String username, String password) throws Exception{           
      try{
                    
          useri = userRepo.getUser(username, password); 
          city = useri.getCityId();
        }catch(Exception e){
          gotoLogin();
      }  


           
           
           try{
               getWeatherLocaly();
               getReminders();
               
               getWeatherOnline();
               
           }catch(Exception e){
                refreshButton.doClick();
           }
    }
    
    /**2)    <Mer reminders>           */
    private void getReminders(){
        try{
           RemindersRepository remindersRep = new RemindersRepository();
           List<Reminders> r = remindersRep.getListOfReminders(useri);
           displayReminder(r);
        }catch(Exception e){
            System.out.println(e);
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
    private void getWeatherLocaly() throws Exception{ 
        Date date = new Date();
        
        try {
        today = weatherRepo.getWeather(city,date);
        //Merr motin lokalisht prej databaze dhe paraqite tek perdoruesi.
        
         displayTodayWeather(today.getCurrent() ,today.getDay(), today.getCond());//Merr motin ditor nga databaza dhe paraqite.
         
            
      dailyList = weatherRepo.getDailyWeather(city,date);//Marrja e motit per Daly nga databaza.
       int i=0;
       //Per secilen daly[d] nga lista[dalylistt],Paraqit tek perdoruesi te dhenat e motit per ate dit.
        //nt i tregon pozicionin e dites nese i-0 dita sitme nese i=1 dita e neserme.
       //Daly d deklarohet e re dhe i jipen te dhenat nga lista.
        
     for(Daily d:dailyList)
           {
       displayDailyWeather(d.getDate(),d.getDay(),d.getCond(),d.getMax(),d.getMin(),i);
        i++;
           }
        
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }
    
    
    /**5)    <Marrja e te dhenave Online>   */ 
    //Mer te dhenat e motit nga YahooWeather dhe ruaj ato lokalisht
    private void getWeatherOnline() throws Exception{  
        //Bashko hostin e URL me ID te qytetit dhe ne fund shto edhe konfigurimet [moti te kthehet si JSON Object]
        URL = URL_HOST + city.getZip() + URL_FOOTER; 
        try{
            JSONObject yahooWeather = weatherRepo.getYahooWeather(URL);
            parseJsonFeed(yahooWeather);
        }catch(IOException | JSONException e){
            throw new Exception(e);
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
            for (int i = 0; i < 6; i++) {
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
            displayTodayWeather(currentTemp,currentDay,currentCondition);
            
        }catch(JSONException | ParseException e){
            System.out.println(e);
        }
    }
    
    


    
    
    /**7)    <Shfaqja e te dhenave tek perdoruesi>    */
    private void displayTodayWeather(int currentTemp,String currentDay,int currentCondition){
        //Paraqit ikonen, dergo kushtet e motit dhe diten e sotme [0 - dita e sotme]
        setIcon(String.valueOf(currentCondition),0);
        
        todayL.setText(currentTemp+" °C");
        todayDay.setText(currentDay);
        
              
    }
    private void displayDailyWeather(Date dailyDate,String dailyDay,short dailyCondition,short dailyMax,short dailyMin,int i) {
    //Paraqit ikonen, dergo kushtet e motit dhe diten [(i=0) sot, (i=1) neser]
        setIcon(String.valueOf(dailyCondition),i);
            switch (i){
                case 0:
                    day0name.setText(dailyDay);
                    day0min.setText("Min. "+dailyMin+" °C");
                    day0max.setText("Max. "+dailyMax+" °C");
                    //setIcon(i, code);*/
                    break;
                case 1:
                    day1name.setText(dailyDay);
                    day1min.setText("Min. "+dailyMin+" °C");
                    day1max.setText("Max. "+dailyMax+" °C");
                    /*setIcon(i, code);*/
                    break;
                case 2:
                    day2name.setText(dailyDay);
                    day2min.setText("Min. "+dailyMin+" °C");
                    day2max.setText("Max. "+dailyMax+" °C");
                    /*setIcon(i, code);*/
                    break;
                case 3:
                    day3name.setText(dailyDay);
                    day3min.setText("Min. "+dailyMin+" °C");
                    day3max.setText("Max. "+dailyMax+" °C");
                    /*setIcon(i, code);*/
                    break;
                case 4:
                    day4name.setText(dailyDay);
                    day4min.setText("Min. "+dailyMin+" °C");
                    day4max.setText("Max. "+dailyMax+" °C");
                    /*setIcon(i, code);/*/
                    break;
                case 5:
                    day5name.setText(dailyDay);
                    day5min.setText("Min. "+dailyMin+" °C");
                    day5max.setText("Max. "+dailyMax+" °C");
                    /*setIcon(i, code);/*/
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
                imageSource = "mot_me_ere.png";
                break;
            case 1:
                imageSource = "vetetima.png";
                break;
            case 2:
                imageSource = "bore_me_shi.png";
                break;
            case 3:
                imageSource = "ngrice.png";
                break;
            case 4:
                imageSource = "rrebesh.png";
                break;
            case 5:
                imageSource = "bore.png";
                break;
            case 6:
                imageSource = "mjegull.png";
                break;
            case 7:
                imageSource = "mot_me_re.png";
                break;
            case 8:
                imageSource = "mot_me_diell.png";
                break;
            case 9:
                imageSource = "riga_lokale_shiu.png";
                break;
            default:
                imageSource = "mot_me_diell.png";
                break;
        }
        //backgroundImgae.setImage(imageSource);
    }
    
    
    
    
    /**10) <Set Rem >*/
    public void displayReminder(List<Reminders> r) throws Exceptions{
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String title="", description="", date="";
        int i=0;
        
        for(Reminders reminder : r){
            title = reminder.getRemindersTitle();
            description = reminder.getRemindersDescription();
            date = df.format(reminder.getRemindersDate());
            
                       
            
            
            switch(i){
                case 0:
                    remind0title.setText(title);
                    remind0text.setText(description);
                    remind0date.setText(date);
                    remind0.setVisible(true);
                    break;
                case 1:
                    remind1title.setText(title);
                    remind1text.setText(description);
                    remind1date.setText(date);
                    remind1.setVisible(true);
                    break;
                case 2:
                    remind2title.setText(title);
                    remind2text.setText(description);
                    remind2date.setText(date);
                    remind2.setVisible(true);
                    break;                    
                case 3:
                    remind3title.setText(title);
                    remind3text.setText(description);
                    remind3date.setText(date);
                    remind3.setVisible(true);
                    break;                    
                    
                    
                    
                     
            }
            
           if(reminder.getRemindersIsset()){
            /*if(remindRepo.isWeatherFriendly(reminder))
                System.out.println("a");*/
            
            }
           i++;
        }
        
    }
   

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel12 = new javax.swing.JPanel();
        motiPanel = new javax.swing.JPanel();
        day1icon = new javax.swing.JLabel();
        day0name = new javax.swing.JLabel();
        day2icon = new javax.swing.JLabel();
        day2name = new javax.swing.JLabel();
        day3icon = new javax.swing.JLabel();
        day3name = new javax.swing.JLabel();
        day4icon = new javax.swing.JLabel();
        day4name = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        todayL = new javax.swing.JLabel();
        day3min = new javax.swing.JLabel();
        day3max = new javax.swing.JLabel();
        day1name = new javax.swing.JLabel();
        day0icon = new javax.swing.JLabel();
        day4min = new javax.swing.JLabel();
        day4max = new javax.swing.JLabel();
        day2min = new javax.swing.JLabel();
        day2max = new javax.swing.JLabel();
        day1min = new javax.swing.JLabel();
        day1max = new javax.swing.JLabel();
        day0min = new javax.swing.JLabel();
        day0max = new javax.swing.JLabel();
        todayIcon = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        day5name = new javax.swing.JLabel();
        day5icon = new javax.swing.JLabel();
        day5max = new javax.swing.JLabel();
        day5min = new javax.swing.JLabel();
        todayDay = new javax.swing.JLabel();
        panelPortal = new javax.swing.JPanel();
        lajminetPanel = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea8 = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea9 = new javax.swing.JTextArea();
        jButton9 = new javax.swing.JButton();
        telegrafPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jButton6 = new javax.swing.JButton();
        ekspresPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton7 = new javax.swing.JButton();
        kosovapressPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea7 = new javax.swing.JTextArea();
        jButton8 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        remanderPanel = new javax.swing.JPanel();
        remind0 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        remind0text = new javax.swing.JTextArea();
        remind0title = new javax.swing.JTextField();
        remind0date = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        remind2 = new javax.swing.JPanel();
        remind2title = new javax.swing.JTextField();
        remind2date = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        remind2text = new javax.swing.JTextArea();
        remind3 = new javax.swing.JPanel();
        remind3title = new javax.swing.JTextField();
        remind3date = new javax.swing.JTextField();
        jScrollPane13 = new javax.swing.JScrollPane();
        remind3text = new javax.swing.JTextArea();
        remind1 = new javax.swing.JPanel();
        remind1title = new javax.swing.JTextField();
        remind1date = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        remind1text = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        share0panel = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        shareTextArea1 = new javax.swing.JTextArea();
        jTextField2 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        share3panel = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        shareTextArea3 = new javax.swing.JTextArea();
        jTextField10 = new javax.swing.JTextField();
        share1panel = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        shareTextArea2 = new javax.swing.JTextArea();
        jTextField11 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel138 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1370, 710));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        motiPanel.setBackground(new java.awt.Color(66, 142, 146));
        motiPanel.setOpaque(false);
        motiPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        day1icon.setForeground(new java.awt.Color(255, 255, 255));
        day1icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Rain_49px.png"))); // NOI18N
        motiPanel.add(day1icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, -1, -1));

        day0name.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        day0name.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day0name, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, -1));

        day2icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Partly Cloudy Rain_49px.png"))); // NOI18N
        motiPanel.add(day2icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, -1, -1));

        day2name.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        day2name.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day2name, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, -1, -1));

        day3icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Fog Day_49px.png"))); // NOI18N
        motiPanel.add(day3icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 60, 50, -1));

        day3name.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        day3name.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day3name, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, -1, -1));

        day4icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/bore_me_shi.png"))); // NOI18N
        motiPanel.add(day4icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 60, -1, 50));

        day4name.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        day4name.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day4name, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 30, -1, -1));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        motiPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, 130));

        todayL.setFont(new java.awt.Font("Calibri", 0, 52)); // NOI18N
        todayL.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(todayL, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        day3min.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        day3min.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day3min, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 110, -1, -1));

        day3max.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        day3max.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day3max, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 130, -1, -1));

        day1name.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        day1name.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day1name, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, -1, -1));

        day0icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Sun_49px.png"))); // NOI18N
        motiPanel.add(day0icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, -1, -1));

        day4min.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        day4min.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day4min, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 110, -1, 20));

        day4max.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        day4max.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day4max, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 130, -1, -1));

        day2min.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        day2min.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day2min, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 110, -1, 20));

        day2max.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        day2max.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day2max, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, -1, -1));

        day1min.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        day1min.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day1min, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, -1, 20));

        day1max.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        day1max.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day1max, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, -1, -1));

        day0min.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        day0min.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day0min, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, -1, 20));

        day0max.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        day0max.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day0max, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, -1, -1));

        todayIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Partly Cloudy Day_49px.png"))); // NOI18N
        motiPanel.add(todayIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, -1, -1));

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Refresh_25px.png"))); // NOI18N
        refreshButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        refreshButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        motiPanel.add(refreshButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        jButton2.setOpaque(false);
        refreshButton.setBackground(new Color(0, 0, 0, 0));
        refreshButton.setForeground(new Color(0, 0, 0, 0));

        jSeparator10.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jSeparator10.setOpaque(true);
        motiPanel.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 2, 1020, 3));

        day5name.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        day5name.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day5name, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 30, -1, -1));

        day5icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Cloud_49px.png"))); // NOI18N
        motiPanel.add(day5icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 60, -1, -1));

        day5max.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        day5max.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day5max, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 130, -1, 20));

        day5min.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        day5min.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(day5min, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 110, -1, 20));

        todayDay.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        todayDay.setForeground(new java.awt.Color(255, 255, 255));
        motiPanel.add(todayDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, -1, -1));

        jPanel12.add(motiPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 1040, 170));

        panelPortal.setBackground(new java.awt.Color(66, 142, 146));
        panelPortal.setForeground(new java.awt.Color(255, 204, 204));
        panelPortal.setOpaque(false);
        panelPortal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lajminetPanel.setBackground(new java.awt.Color(204, 204, 204));
        lajminetPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lajminetPanel.setOpaque(false);
        lajminetPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea8.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea8.setColumns(20);
        jTextArea8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jTextArea8.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea8.setLineWrap(true);
        jTextArea8.setRows(5);
        jTextArea8.setText("Arrestoi i dyshuari i gjashtë për sulmin në Mançester");
        jTextArea8.setWrapStyleWord(true);
        jScrollPane8.setViewportView(jTextArea8);
        jScrollPane8.setOpaque(false);
        jScrollPane8.getViewport().setOpaque(false);
        jScrollPane8.setBorder(null);
        jScrollPane8.setViewportBorder(null);

        jTextArea8.setBorder(null);
        jTextArea8.setBackground(new Color(0, 0, 0, 0));

        jTextArea8.setWrapStyleWord(true);
        jTextArea8.setLineWrap(true);
        jTextArea8.setOpaque(false);
        jTextArea8.setEditable(false);
        jTextArea8.setFocusable(false);

        lajminetPanel.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 60));

        jScrollPane9.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane9.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea9.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea9.setColumns(20);
        jTextArea9.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextArea9.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea9.setLineWrap(true);
        jTextArea9.setRows(5);
        jTextArea9.setText("Ky 83-vjeçar nga Obiliqi, që është tifoz i flaktë edhe i skuadrës “KEK”, rrëfen këtë “pasion” të pazakontë të tij.\n\nPër më shumë, shikojeni storjet e përgatitur nga Klan Kosova.");
        jScrollPane9.setViewportView(jTextArea9);
        jScrollPane9.setOpaque(false);
        jScrollPane9.getViewport().setOpaque(false);
        jScrollPane9.setBorder(null);
        jScrollPane9.setViewportBorder(null);

        jTextArea9.setBorder(null);
        jTextArea9.setBackground(new Color(0, 0, 0, 0));

        jTextArea9.setWrapStyleWord(true);
        jTextArea9.setLineWrap(true);
        jTextArea9.setOpaque(false);
        jTextArea9.setEditable(false);
        jTextArea9.setFocusable(false);

        lajminetPanel.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 210, 230));

        jButton9.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Shikoni me shume..");
        jButton9.setBorder(null);
        jButton9.setContentAreaFilled(false);
        lajminetPanel.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        panelPortal.add(lajminetPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 40, 230, 330));
        lajminetPanel.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
        lajminetPanel.setForeground(new Color(1.0f,1.0f,1.0f,0.5f));

        telegrafPanel.setBackground(new java.awt.Color(204, 204, 204));
        telegrafPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        telegrafPanel.setForeground(new java.awt.Color(255, 255, 255));
        telegrafPanel.setOpaque(false);
        telegrafPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea4.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea4.setColumns(20);
        jTextArea4.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jTextArea4.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea4.setLineWrap(true);
        jTextArea4.setRows(5);
        jTextArea4.setText("Arrestoi i dyshuari i gjashtë për sulmin në Mançester");
        jTextArea4.setWrapStyleWord(true);
        jScrollPane4.setViewportView(jTextArea4);
        jScrollPane4.setOpaque(false);
        jScrollPane4.getViewport().setOpaque(false);
        jScrollPane4.setBorder(null);
        jScrollPane4.setViewportBorder(null);

        jTextArea4.setBorder(null);
        jTextArea4.setBackground(new Color(0, 0, 0, 0));

        jTextArea4.setWrapStyleWord(true);
        jTextArea4.setLineWrap(true);
        jTextArea4.setOpaque(false);
        jTextArea4.setEditable(false);
        jTextArea4.setFocusable(false);

        telegrafPanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 220, 60));

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea5.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea5.setColumns(20);
        jTextArea5.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextArea5.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea5.setLineWrap(true);
        jTextArea5.setRows(5);
        jTextArea5.setText("Ky 83-vjeçar nga Obiliqi, që është tifoz i flaktë edhe i skuadrës “KEK”, rrëfen këtë “pasion” të pazakontë të tij.\n\nPër më shumë, shikojeni storjet e përgatitur nga Klan Kosova.");
        jScrollPane5.setViewportView(jTextArea5);
        jScrollPane5.setOpaque(false);
        jScrollPane5.getViewport().setOpaque(false);
        jScrollPane5.setBorder(null);
        jScrollPane5.setViewportBorder(null);

        jTextArea5.setBorder(null);
        jTextArea5.setBackground(new Color(0, 0, 0, 0));

        jTextArea5.setWrapStyleWord(true);
        jTextArea5.setLineWrap(true);
        jTextArea5.setOpaque(false);
        jTextArea5.setEditable(false);
        jTextArea5.setFocusable(false);

        telegrafPanel.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 220, 230));

        jButton6.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Shikoni me shume..");
        jButton6.setBorder(null);
        jButton6.setContentAreaFilled(false);
        telegrafPanel.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        panelPortal.add(telegrafPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 240, 330));
        telegrafPanel.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
        telegrafPanel.setForeground(new Color(1.0f,1.0f,1.0f,0.5f));

        ekspresPanel.setBackground(new java.awt.Color(204, 204, 204));
        ekspresPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        ekspresPanel.setOpaque(false);
        ekspresPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea2.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jTextArea2.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setText("Arrestoi i dyshuari i gjashtë për sulmin në Mançester");
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setAutoscrolls(false);
        jScrollPane2.setViewportView(jTextArea2);
        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.setBorder(null);
        jScrollPane2.setViewportBorder(null);

        jTextArea2.setBorder(null);
        jTextArea2.setBackground(new Color(0, 0, 0, 0));

        jTextArea2.setLineWrap(true);
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setLineWrap(true);
        jTextArea2.setOpaque(false);
        jTextArea2.setEditable(false);
        jTextArea2.setFocusable(false);

        ekspresPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 220, 60));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Ky 83-vjeçar nga Obiliqi, që është\n tifoz i flaktë edhe i skuadrës “KEK”, rrëfen këtë “pasion” të pazakontë të tij.\n\nPër më shumë, shikojeni storjet e përgatitur nga Klan Kosova.");
        jScrollPane1.setViewportView(jTextArea1);
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBorder(null);
        jScrollPane1.setViewportBorder(null);

        jTextArea1.setBorder(null);
        jTextArea1.setBackground(new Color(0, 0, 0, 0));

        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setLineWrap(true);
        jTextArea1.setOpaque(false);
        jTextArea1.setEditable(false);
        jTextArea1.setFocusable(false);

        ekspresPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 220, 230));

        jButton7.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Shikoni me shume..");
        jButton7.setBorder(null);
        jButton7.setContentAreaFilled(false);
        ekspresPanel.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        panelPortal.add(ekspresPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 240, 330));
        ekspresPanel.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
        ekspresPanel.setForeground(new Color(1.0f,1.0f,1.0f,0.5f));

        kosovapressPanel.setBackground(new java.awt.Color(204, 204, 204));
        kosovapressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        kosovapressPanel.setOpaque(false);
        kosovapressPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setToolTipText("");
        jScrollPane6.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea6.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea6.setColumns(20);
        jTextArea6.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jTextArea6.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea6.setLineWrap(true);
        jTextArea6.setRows(5);
        jTextArea6.setText("Arrestoi i dyshuari i gjashtë për sulmin në Mançester");
        jTextArea6.setWrapStyleWord(true);
        jScrollPane6.setViewportView(jTextArea6);
        jScrollPane6.setOpaque(false);
        jScrollPane6.getViewport().setOpaque(false);
        jScrollPane6.setBorder(null);
        jScrollPane6.setViewportBorder(null);

        jTextArea6.setBorder(null);
        jTextArea6.setBackground(new Color(0, 0, 0, 0));

        jTextArea6.setWrapStyleWord(true);
        jTextArea6.setLineWrap(true);
        jTextArea6.setOpaque(false);
        jTextArea6.setEditable(false);
        jTextArea6.setFocusable(false);

        kosovapressPanel.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 60));

        jScrollPane7.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane7.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea7.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea7.setColumns(20);
        jTextArea7.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextArea7.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea7.setLineWrap(true);
        jTextArea7.setRows(5);
        jTextArea7.setText("Ky 83-vjeçar nga Obiliqi, që është tifoz i flaktë edhe i skuadrës “KEK”, rrëfen këtë “pasion” të pazakontë të tij.\n\nPër më shumë, shikojeni storjet e përgatitur nga Klan Kosova.");
        jScrollPane7.setViewportView(jTextArea7);
        jScrollPane7.setOpaque(false);
        jScrollPane7.getViewport().setOpaque(false);
        jScrollPane7.setBorder(null);
        jScrollPane7.setViewportBorder(null);

        jTextArea7.setBorder(null);
        jTextArea7.setBackground(new Color(0, 0, 0, 0));

        jTextArea7.setWrapStyleWord(true);
        jTextArea7.setLineWrap(true);
        jTextArea7.setOpaque(false);
        jTextArea7.setEditable(false);
        jTextArea7.setFocusable(false);

        kosovapressPanel.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 210, 230));

        jButton8.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Shikoni me shume..");
        jButton8.setBorder(null);
        jButton8.setContentAreaFilled(false);
        kosovapressPanel.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        panelPortal.add(kosovapressPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, 230, 330));
        kosovapressPanel.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
        kosovapressPanel.setForeground(new Color(1.0f,1.0f,1.0f,0.5f));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("LajmiNet");
        panelPortal.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Telegrafi");
        panelPortal.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Gazeta Express");
        panelPortal.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Kosova Press");
        panelPortal.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

        jPanel12.add(panelPortal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 400));

        remanderPanel.setBackground(new java.awt.Color(66, 142, 146));
        remanderPanel.setOpaque(false);
        remanderPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        remind0.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        remind0.setOpaque(false);
        remind0.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        remind0text.setBackground(new java.awt.Color(0, 0, 0));
        remind0text.setColumns(20);
        remind0text.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        remind0text.setForeground(new java.awt.Color(255, 255, 255));
        remind0text.setLineWrap(true);
        remind0text.setRows(5);
        remind0text.setText("Trajnimi do te behet ne Lipjane. Ne ora    13:00.");
        jScrollPane3.setViewportView(remind0text);
        jScrollPane3.setOpaque(false);
        jScrollPane3.getViewport().setOpaque(false);
        jScrollPane3.setBorder(null);
        jScrollPane3.setViewportBorder(null);

        remind0text.setBorder(null);
        remind0text.setBackground(new Color(0, 0, 0, 0));

        remind0text.setWrapStyleWord(true);
        remind0text.setLineWrap(true);
        remind0text.setOpaque(false);
        remind0text.setEditable(false);
        remind0text.setFocusable(false);

        remind0.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 260, 60));

        remind0title.setBackground(new java.awt.Color(0, 0, 0));
        remind0title.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        remind0title.setForeground(new java.awt.Color(255, 255, 255));
        remind0title.setText("Trajnime ne UBT Campus");
        remind0title.setBorder(null);
        remind0.add(remind0title, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));
        remind0title.setOpaque(false);
        remind0title.setBackground(new Color(0, 0, 0, 0));
        remind0title.setEditable(false);
        remind0title.setFocusable(false);

        remind0date.setBackground(new java.awt.Color(0, 0, 0));
        remind0date.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        remind0date.setForeground(new java.awt.Color(255, 255, 255));
        remind0date.setText("26.05.2017");
        remind0date.setBorder(null);
        remind0.add(remind0date, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));
        remind0date.setOpaque(false);
        remind0date.setBackground(new Color(0, 0, 0, 0));
        remind0date.setEditable(false);
        remind0date.setFocusable(false);

        remanderPanel.add(remind0, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 300, 110));
        remind0.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
        remind0.setForeground(new Color(1.0f,1.0f,1.0f,0.5f));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Plus_50px.png"))); // NOI18N
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        remanderPanel.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 650, 40, 40));
        jButton3.setOpaque(false);
        jButton3.setBackground(new Color(0, 0, 0, 00));
        jButton3.setForeground(new Color(0, 0, 0, 20));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Settings_28px.png"))); // NOI18N
        jButton4.setContentAreaFilled(false);
        remanderPanel.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 40, 30));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Activity Feed_50px.png"))); // NOI18N
        jButton5.setContentAreaFilled(false);
        remanderPanel.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 640, -1, -1));

        remind2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        remind2.setAlignmentX(0.0F);
        remind2.setAlignmentY(0.0F);
        remind2.setOpaque(false);
        remind2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        remind2title.setBackground(new java.awt.Color(0, 0, 0));
        remind2title.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        remind2title.setForeground(new java.awt.Color(255, 255, 255));
        remind2title.setText("Trajnime ne UBT Campus");
        remind2title.setBorder(null);
        remind2title.setCaretColor(new java.awt.Color(255, 255, 255));
        remind2.add(remind2title, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));
        remind2title.setOpaque(false);
        remind2title.setBackground(new Color(0, 0, 0, 0));
        remind2title.setEditable(false);
        remind2title.setFocusable(false);

        remind2date.setBackground(new java.awt.Color(0, 0, 0));
        remind2date.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        remind2date.setForeground(new java.awt.Color(255, 255, 255));
        remind2date.setText("26.05.2017");
        remind2date.setBorder(null);
        remind2.add(remind2date, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));
        remind2date.setOpaque(false);
        remind2date.setBackground(new Color(0, 0, 0, 0));
        remind2date.setEditable(false);
        remind2date.setFocusable(false);

        jScrollPane12.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane12.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        remind2text.setBackground(new java.awt.Color(0, 0, 0));
        remind2text.setColumns(20);
        remind2text.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        remind2text.setForeground(new java.awt.Color(255, 255, 255));
        remind2text.setLineWrap(true);
        remind2text.setRows(5);
        remind2text.setText("Trajnimi do te behet ne Lipjane. Ne ora    13:00.");
        jScrollPane12.setViewportView(remind2text);
        jScrollPane12.setOpaque(false);
        jScrollPane12.getViewport().setOpaque(false);
        jScrollPane12.setBorder(null);
        jScrollPane12.setViewportBorder(null);

        remind2text.setBorder(null);
        remind2text.setBackground(new Color(0, 0, 0, 0));

        remind2text.setWrapStyleWord(true);
        remind2text.setLineWrap(true);
        remind2text.setOpaque(false);
        remind2text.setEditable(false);
        remind2text.setFocusable(false);

        remind2.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 260, 60));

        remanderPanel.add(remind2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 300, 110));
        remind2.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
        remind2.setForeground(new Color(01.0f,1.0f,1.0f,0.5f));

        remind3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        remind3.setAlignmentX(0.0F);
        remind3.setAlignmentY(0.0F);
        remind3.setOpaque(false);
        remind3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        remind3title.setBackground(new java.awt.Color(0, 0, 0));
        remind3title.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        remind3title.setForeground(new java.awt.Color(255, 255, 255));
        remind3title.setText("Trajnime ne UBT Campus");
        remind3title.setBorder(null);
        remind3title.setCaretColor(new java.awt.Color(255, 255, 255));
        remind3.add(remind3title, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));
        remind3title.setOpaque(false);
        remind3title.setBackground(new Color(0, 0, 0, 0));
        remind3title.setEditable(false);
        remind3title.setFocusable(false);

        remind3date.setBackground(new java.awt.Color(0, 0, 0));
        remind3date.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        remind3date.setForeground(new java.awt.Color(255, 255, 255));
        remind3date.setText("26.05.2017");
        remind3date.setBorder(null);
        remind3.add(remind3date, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));
        remind3date.setOpaque(false);
        remind3date.setBackground(new Color(0, 0, 0, 0));
        remind3date.setEditable(false);
        remind3date.setFocusable(false);

        jScrollPane13.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane13.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        remind3text.setBackground(new java.awt.Color(0, 0, 0));
        remind3text.setColumns(20);
        remind3text.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        remind3text.setForeground(new java.awt.Color(255, 255, 255));
        remind3text.setLineWrap(true);
        remind3text.setRows(5);
        remind3text.setText("Trajnimi do te behet ne Lipjane. Ne ora    13:00.");
        jScrollPane13.setViewportView(remind3text);
        jScrollPane13.setOpaque(false);
        jScrollPane13.getViewport().setOpaque(false);
        jScrollPane13.setBorder(null);
        jScrollPane13.setViewportBorder(null);

        remind3text.setBorder(null);
        remind3text.setBackground(new Color(0, 0, 0, 0));

        remind3text.setWrapStyleWord(true);
        remind3text.setLineWrap(true);
        remind3text.setOpaque(false);
        remind3text.setEditable(false);
        remind3text.setFocusable(false);

        remind3.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 260, 60));

        remanderPanel.add(remind3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 300, 110));
        remind3.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
        remind3.setForeground(new Color(1.0f,1.0f,1.0f,0.5f));

        remind1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        remind1.setAlignmentX(0.0F);
        remind1.setAlignmentY(0.0F);
        remind1.setOpaque(false);
        remind1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        remind1title.setBackground(new java.awt.Color(0, 0, 0));
        remind1title.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        remind1title.setForeground(new java.awt.Color(255, 255, 255));
        remind1title.setText("Trajnime ne UBT Campus");
        remind1title.setBorder(null);
        remind1.add(remind1title, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));
        remind1title.setOpaque(false);
        remind1title.setBackground(new Color(0, 0, 0, 0));
        remind1title.setEditable(false);
        remind1title.setFocusable(false);

        remind1date.setBackground(new java.awt.Color(0, 0, 0));
        remind1date.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        remind1date.setForeground(new java.awt.Color(255, 255, 255));
        remind1date.setText("26.05.2017");
        remind1date.setBorder(null);
        remind1.add(remind1date, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));
        remind1date.setOpaque(false);
        remind1date.setBackground(new Color(0, 0, 0, 0));
        remind1date.setEditable(false);
        remind1date.setFocusable(false);

        jScrollPane11.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane11.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        remind1text.setBackground(new java.awt.Color(0, 0, 0));
        remind1text.setColumns(20);
        remind1text.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        remind1text.setForeground(new java.awt.Color(255, 255, 255));
        remind1text.setLineWrap(true);
        remind1text.setRows(5);
        remind1text.setText("Trajnimi do te behet ne Lipjane. Ne ora    13:00.");
        jScrollPane11.setViewportView(remind1text);
        jScrollPane11.setOpaque(false);
        jScrollPane11.getViewport().setOpaque(false);
        jScrollPane11.setBorder(null);
        jScrollPane11.setViewportBorder(null);

        remind1text.setBorder(null);
        remind1text.setBackground(new Color(0, 0, 0, 0));

        remind1text.setWrapStyleWord(true);
        remind1text.setLineWrap(true);
        remind1text.setOpaque(false);
        remind1text.setEditable(false);
        remind1text.setFocusable(false);

        remind1.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 260, 60));

        remanderPanel.add(remind1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 110));
        remind1.setBackground(new Color(01.0f,1.0f,1.0f,0.5f));
        remind1.setForeground(new Color(1.0f,1.0f,1.0f,0.5f));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/More_30px.png"))); // NOI18N
        jButton2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        remanderPanel.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, -1, 20));
        jButton2.setOpaque(false);
        jButton2.setBackground(new Color(0, 0, 0, 20));
        jButton2.setForeground(new Color(0, 0, 0, 20));

        jPanel12.add(remanderPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 320, 710));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jSeparator3.setOpaque(true);
        jPanel12.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1042, 40, 3, 670));

        jPanel4.setBackground(new java.awt.Color(66, 142, 146));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        share0panel.setBackground(new java.awt.Color(204, 204, 204));
        share0panel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        share0panel.setForeground(new java.awt.Color(255, 255, 255));
        share0panel.setOpaque(false);
        share0panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane10.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane10.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        shareTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        shareTextArea1.setColumns(20);
        shareTextArea1.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        shareTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        shareTextArea1.setLineWrap(true);
        shareTextArea1.setRows(5);
        shareTextArea1.setText("Takime ne ora 13:00 ne UBT\nCAMPUS");
        jScrollPane10.setViewportView(shareTextArea1);
        jScrollPane10.setOpaque(false);
        jScrollPane10.getViewport().setOpaque(false);
        jScrollPane10.setBorder(null);
        jScrollPane10.setViewportBorder(null);

        shareTextArea1.setBorder(null);
        shareTextArea1.setBackground(new Color(0, 0, 0, 0));

        shareTextArea1.setWrapStyleWord(true);
        shareTextArea1.setLineWrap(true);
        shareTextArea1.setOpaque(false);
        shareTextArea1.setEditable(false);
        shareTextArea1.setFocusable(false);

        share0panel.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 190, 60));

        jTextField2.setBackground(new java.awt.Color(0, 0, 0));
        jTextField2.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setText("Laura Berisha");
        jTextField2.setBorder(null);
        share0panel.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));
        jTextField2.setOpaque(false);
        jTextField2.setBackground(new Color(0, 0, 0, 0));
        jTextField2.setEditable(false);
        jTextField2.setFocusable(false);

        jPanel4.add(share0panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 210, 100));
        share0panel.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
        share0panel.setForeground(new Color(1.0f,1.0f,1.0f,0.5f));

        jButton10.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/More_30px.png"))); // NOI18N
        jButton10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 40, -1, 20));
        jButton10.setOpaque(false);
        jButton10.setBackground(new Color (0, 0, 0, 20));
        jButton10.setForeground(new Color(0, 0, 0, 20));

        jButton10.setOpaque(false);
        jButton10.setBackground(new Color(0, 0, 0, 150));

        share3panel.setBackground(new java.awt.Color(204, 204, 204));
        share3panel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        share3panel.setForeground(new java.awt.Color(255, 255, 255));
        share3panel.setOpaque(false);
        share3panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane14.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane14.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        shareTextArea3.setBackground(new java.awt.Color(0, 0, 0));
        shareTextArea3.setColumns(20);
        shareTextArea3.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        shareTextArea3.setForeground(new java.awt.Color(255, 255, 255));
        shareTextArea3.setLineWrap(true);
        shareTextArea3.setRows(5);
        shareTextArea3.setText("Takime ne ora 13:00 ne UBT\nCAMPUS");
        jScrollPane14.setViewportView(shareTextArea3);
        jScrollPane14.setOpaque(false);
        jScrollPane14.getViewport().setOpaque(false);
        jScrollPane14.setBorder(null);
        jScrollPane14.setViewportBorder(null);

        shareTextArea3.setBorder(null);
        shareTextArea3.setBackground(new Color(0, 0, 0, 0));

        shareTextArea3.setWrapStyleWord(true);
        shareTextArea3.setLineWrap(true);
        shareTextArea3.setOpaque(false);
        shareTextArea3.setEditable(false);
        shareTextArea3.setFocusable(false);

        share3panel.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 190, 60));

        jTextField10.setBackground(new java.awt.Color(0, 0, 0));
        jTextField10.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(255, 255, 255));
        jTextField10.setText("Laura Berisha");
        jTextField10.setBorder(null);
        share3panel.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));
        jTextField10.setOpaque(false);
        jTextField10.setBackground(new Color(0, 0, 0, 0));
        jTextField10.setEditable(false);
        jTextField10.setFocusable(false);

        jPanel4.add(share3panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 210, 100));
        share3panel.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
        share3panel.setForeground(new Color(1.0f,1.0f,1.0f,0.5f));

        share1panel.setBackground(new java.awt.Color(204, 204, 204));
        share1panel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        share1panel.setForeground(new java.awt.Color(255, 255, 255));
        share1panel.setOpaque(false);
        share1panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane15.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane15.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        shareTextArea2.setBackground(new java.awt.Color(0, 0, 0));
        shareTextArea2.setColumns(20);
        shareTextArea2.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        shareTextArea2.setForeground(new java.awt.Color(255, 255, 255));
        shareTextArea2.setLineWrap(true);
        shareTextArea2.setRows(5);
        shareTextArea2.setText("Takime ne ora 13:00 ne UBT\nCAMPUS");
        jScrollPane15.setViewportView(shareTextArea2);
        jScrollPane15.setOpaque(false);
        jScrollPane15.getViewport().setOpaque(false);
        jScrollPane15.setBorder(null);
        jScrollPane15.setViewportBorder(null);

        shareTextArea2.setBorder(null);
        shareTextArea2.setBackground(new Color(0, 0, 0, 0));

        shareTextArea2.setWrapStyleWord(true);
        shareTextArea2.setLineWrap(true);
        shareTextArea2.setOpaque(false);
        shareTextArea2.setEditable(false);
        shareTextArea2.setFocusable(false);

        share1panel.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 190, 60));

        jTextField11.setBackground(new java.awt.Color(0, 0, 0));
        jTextField11.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jTextField11.setForeground(new java.awt.Color(255, 255, 255));
        jTextField11.setText("Laura Berisha");
        jTextField11.setBorder(null);
        share1panel.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));
        jTextField11.setOpaque(false);
        jTextField11.setBackground(new Color(0, 0, 0, 0));
        jTextField11.setEditable(false);
        jTextField11.setFocusable(false);

        jPanel4.add(share1panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 210, 100));
        share1panel.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
        share1panel.setForeground(new Color(1.0f,1.0f,1.0f,0.5f));

        jPanel12.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 1040, 120));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jSeparator2.setOpaque(true);
        jPanel12.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 392, 1020, 3));

        jLabel138.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/background1.jpg"))); // NOI18N
        jPanel12.add(jLabel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
        try
        {
            getWeatherOnline();
            getReminders();
            JOptionPane.showMessageDialog(this, "Moti u perditesua me sukses !");
        }
        catch(Exception e){
            try{
            getWeatherLocaly();
            }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Nuk ka te dhena te motit te ruajtura lokalisht !");
            }
         JOptionPane.showMessageDialog(this, "Lidhja me serverin deshtoi !\nKontrolloni lidhjen e internetit !");
        }
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        AddReg adreg = new AddReg();
        adreg.setVisible(true);
        adreg.checkUser(useri.getUsername(),useri.getPassword());
        
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }
  
    

 
    
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel day0icon;
    private javax.swing.JLabel day0max;
    private javax.swing.JLabel day0min;
    private javax.swing.JLabel day0name;
    private javax.swing.JLabel day1icon;
    private javax.swing.JLabel day1max;
    private javax.swing.JLabel day1min;
    private javax.swing.JLabel day1name;
    private javax.swing.JLabel day2icon;
    private javax.swing.JLabel day2max;
    private javax.swing.JLabel day2min;
    private javax.swing.JLabel day2name;
    private javax.swing.JLabel day3icon;
    private javax.swing.JLabel day3max;
    private javax.swing.JLabel day3min;
    private javax.swing.JLabel day3name;
    private javax.swing.JLabel day4icon;
    private javax.swing.JLabel day4max;
    private javax.swing.JLabel day4min;
    private javax.swing.JLabel day4name;
    private javax.swing.JLabel day5icon;
    private javax.swing.JLabel day5max;
    private javax.swing.JLabel day5min;
    private javax.swing.JLabel day5name;
    private javax.swing.JPanel ekspresPanel;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JTextArea jTextArea8;
    private javax.swing.JTextArea jTextArea9;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPanel kosovapressPanel;
    private javax.swing.JPanel lajminetPanel;
    private javax.swing.JPanel motiPanel;
    private javax.swing.JPanel panelPortal;
    private javax.swing.JButton refreshButton;
    private javax.swing.JPanel remanderPanel;
    private javax.swing.JPanel remind0;
    private javax.swing.JTextField remind0date;
    private javax.swing.JTextArea remind0text;
    private javax.swing.JTextField remind0title;
    private javax.swing.JPanel remind1;
    private javax.swing.JTextField remind1date;
    private javax.swing.JTextArea remind1text;
    private javax.swing.JTextField remind1title;
    private javax.swing.JPanel remind2;
    private javax.swing.JTextField remind2date;
    private javax.swing.JTextArea remind2text;
    private javax.swing.JTextField remind2title;
    private javax.swing.JPanel remind3;
    private javax.swing.JTextField remind3date;
    private javax.swing.JTextArea remind3text;
    private javax.swing.JTextField remind3title;
    private javax.swing.JPanel share0panel;
    private javax.swing.JPanel share1panel;
    private javax.swing.JPanel share3panel;
    private javax.swing.JTextArea shareTextArea1;
    private javax.swing.JTextArea shareTextArea2;
    private javax.swing.JTextArea shareTextArea3;
    private javax.swing.JPanel telegrafPanel;
    private javax.swing.JLabel todayDay;
    private javax.swing.JLabel todayIcon;
    private javax.swing.JLabel todayL;
    // End of variables declaration//GEN-END:variables
}
