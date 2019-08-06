package hello;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;


public class Company {

//    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
    private String symbol;

    @Embedded
    private DailyTimeSeries DailyTimeSeries;

    public Company() {}

    public Company(String ticker){
        this.symbol = ticker;
    }
}




