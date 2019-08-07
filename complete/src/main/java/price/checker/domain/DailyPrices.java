package price.checker.domain;

import javax.persistence.Embeddable;
import java.util.*;

/**
 * This class contains stock price information for a single day
 * This class is embeddable by the Date entity
 */
@Embeddable
public class DailyPrices {

    private HashMap<String,Double> prices;

    public DailyPrices(){}

    public DailyPrices(Double open, Double high, Double low, Double close, Double volume) {
        this.prices = new HashMap<>();
        this.prices.put("open", open);
        this.prices.put("high", high);
        this.prices.put("low", low);
        this.prices.put("close", close);
        this.prices.put("volume", volume);
    }
}
