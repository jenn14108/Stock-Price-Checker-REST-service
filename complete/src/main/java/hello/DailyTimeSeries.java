package hello;

import javax.persistence.Embedded;
import javax.persistence.Embeddable;

@Embeddable
public class DailyTimeSeries {

    private String title;

    @Embedded
    private Date Date;

    public DailyTimeSeries(){}


}
