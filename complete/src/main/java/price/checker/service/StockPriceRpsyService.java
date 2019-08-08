package price.checker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import price.checker.domain.rpsy.StockPriceRepository;
import price.checker.domain.StockPrice;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the service responsible for updating, deleting, retrieving
 * and adding new data entries into mySQL table "StockPrice"
 */

@Slf4j
@Service
public class StockPriceRpsyService {

    @Autowired
    private StockPriceRepository stockPriceRepository;
    
    public void saveStockPrice() throws ParseException {
        String dateString = "2019-08-07";
        String pattern = "yyyy-MM-dd";
        Date d = new SimpleDateFormat(pattern).parse(dateString);

        StockPrice sp = new StockPrice("TSLA", d, 226.5, 229.98,
                                        225.8,228.7,2440068);
        stockPriceRepository.save(sp);
        log.info("We have successfully saved new object {} into db",
                sp.getSymbol());
    }

}
