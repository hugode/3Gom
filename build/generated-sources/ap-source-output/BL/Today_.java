package BL;

import BL.City;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-17T10:00:33")
@StaticMetamodel(Today.class)
public class Today_ { 

    public static volatile SingularAttribute<Today, Integer> id;
    public static volatile SingularAttribute<Today, Short> min;
    public static volatile SingularAttribute<Today, Short> max;
    public static volatile SingularAttribute<Today, City> cityId;
    public static volatile SingularAttribute<Today, String> day;
    public static volatile SingularAttribute<Today, Date> date;
    public static volatile SingularAttribute<Today, Short> cond;

}