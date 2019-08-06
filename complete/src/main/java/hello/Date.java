package hello;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.List;

@Embeddable
public class Date {

    private String date;

    @Embedded
    private List<DailyPrices> dailyPrices;

    public Date(){}


}
