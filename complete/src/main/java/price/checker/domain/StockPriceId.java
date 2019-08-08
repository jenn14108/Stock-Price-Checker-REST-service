package price.checker.domain;


import java.io.Serializable;
import java.util.Date;

public class StockPriceId implements Serializable {

    private String symbol;
    private Date date;

    public StockPriceId(){}

    public StockPriceId(String symbol, Date date){
        this.symbol = symbol;
        this.date = date;
    }

    @Override
    public boolean equals(Object o){
        return false;
    }

    @Override
    public int hashCode(){
        return 0;
    }
}
