package price.checker.domain;

import price.checker.domain.DailyPrices;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * This class is embeddable by the DailyTimeSeries entity
 */
@Embeddable
public class Date {

    private String date;

    @Embedded
    private DailyPrices dailyPrices;

    public Date(){}

    public Date(String date, DailyPrices dailyPrices){
        this.date = date;
        this.dailyPrices = dailyPrices;
    }

    public String getDate(){
        return this.date;
    }
}
