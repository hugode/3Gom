package BL;

import BL.City;
import BL.UsersPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-17T10:00:33")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, City> cityId;
    public static volatile SingularAttribute<Users, String> name;
    public static volatile SingularAttribute<Users, UsersPK> usersPK;
    public static volatile SingularAttribute<Users, String> password;

}