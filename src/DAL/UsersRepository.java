/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;


import BL.City;
import BL.Users;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Aztech.Web
 */
public class UsersRepository extends EntityManagerClass{
    
    //Gjej perdoruesin bazuar ne Username dhe Password
    public Users getUser(String u,String p) throws Exceptions {
        Query query = em.createQuery("SELECT u FROM Users u WHERE u.usersPK.username=:u AND u.password=:p");
        query.setParameter("u",u);
        query.setParameter("p",p);
        try{
            return (Users) query.getSingleResult();
        }catch(NoResultException e){
            throw new Exceptions("Nuk egziston asnje perdorues me keto te dhena !");
        }
    }
    
    public City getCity(int id) throws Exceptions{
        Query query = em.createQuery("SELECT c FROM City c WHERE c.id=:id");
        query.setParameter("id",id);

        try{
            return (City) query.getSingleResult();
        }catch(NoResultException e){
            throw new Exceptions("Nuk qytet asnje perdorues me keto te dhena !");
        }
    }
    
    //Perkthe emrin e dites
    public static String setDay(String Day){      
        switch (Day) { 
            case "Mon":
                return "E Hene";
            case "Tue": 
                return "E Marte";
            case "Wed": 
                return "E Merkure";
            case "Thu": 
                return "E Enjete";
            case "Fri": 
                return "E Premte";
            case "Sat": 
                return "E Shtunë";
            case "Sun": 
                return "E Diel";
        }
        return null;        
    }
    
    //kontrollo per hapsira ne Username
    public boolean hasSpaces(String s){
    Pattern pattern = Pattern.compile("\\s");
    Matcher matcher = pattern.matcher(s);
    return  matcher.find();
    }
    public boolean hasSpecialChars(String s){
    //kontrollo per cdo karakter tjeter perveq A-Z, 0-9 dhe . (pika)
    //nuk ka rendesi nese karakteret jane shkronja te medha ose te vogla (CASE_INSENSITIVE)
    Pattern p = Pattern.compile("[^a-z0-9. ]", Pattern.CASE_INSENSITIVE);
    Matcher m = p.matcher(s);
    return  m.find();
    }
        
    
}
