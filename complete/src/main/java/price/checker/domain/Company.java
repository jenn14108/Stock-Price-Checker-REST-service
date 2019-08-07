package price.checker.domain;

import javax.persistence.Embedded;

/**
 * This is the company model/schema that will be used in MySQL
 * This model will have a simple field for its ticker, and an embedded field
 * for all of its price data titled "DailyTimeSeries"
 */
public class Company {

//    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
    private String symbol;

    @Embedded
    private price.checker.domain.DailyTimeSeries DailyTimeSeries;

    public Company() {}

    public Company(String ticker){
        this.symbol = ticker;
    }
}




