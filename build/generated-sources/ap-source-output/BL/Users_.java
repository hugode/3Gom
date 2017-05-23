package BL;

import BL.City;
import BL.Reminder;
import BL.UsersPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-22T10:30:48")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> password;
    public static volatile CollectionAttribute<Users, Reminder> reminderCollection;
    public static volatile SingularAttribute<Users, UsersPK> usersPK;
    public static volatile SingularAttribute<Users, String> name;
    public static volatile SingularAttribute<Users, City> cityId;

}