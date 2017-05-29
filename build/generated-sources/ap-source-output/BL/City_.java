package BL;

import BL.Daily;
import BL.Today;
import BL.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-29T10:16:39")
@StaticMetamodel(City.class)
public class City_ { 

    public static volatile SingularAttribute<City, Integer> zip;
    public static volatile SingularAttribute<City, String> name;
    public static volatile CollectionAttribute<City, Daily> dailyCollection;
    public static volatile SingularAttribute<City, Integer> id;
    public static volatile CollectionAttribute<City, Today> todayCollection;
    public static volatile CollectionAttribute<City, Users> usersCollection;

}