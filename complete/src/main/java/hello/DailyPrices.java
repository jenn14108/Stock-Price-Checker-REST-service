package hello;

import javax.persistence.Embeddable;

@Embeddable
public class DailyPrices {

    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;

    public DailyPrices(){}
}
