package BL;

import BL.City;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-17T15:03:49")
@StaticMetamodel(Daily.class)
public class Daily_ { 

    public static volatile SingularAttribute<Daily, Short> min;
    public static volatile SingularAttribute<Daily, Short> max;
    public static volatile SingularAttribute<Daily, City> cityId;
    public static volatile SingularAttribute<Daily, Integer> dailyId;
    public static volatile SingularAttribute<Daily, String> day;
    public static volatile SingularAttribute<Daily, Date> date;
    public static volatile SingularAttribute<Daily, Short> cond;

}