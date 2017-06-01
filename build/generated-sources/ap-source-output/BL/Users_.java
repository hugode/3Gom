package BL;

import BL.City;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-31T16:32:55")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> password;
    public static volatile SingularAttribute<Users, String> name;
    public static volatile SingularAttribute<Users, Integer> id;
    public static volatile SingularAttribute<Users, City> cityId;
    public static volatile SingularAttribute<Users, String> username;

}