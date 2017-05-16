/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;


import BL.Users;
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
       
        
    
}
