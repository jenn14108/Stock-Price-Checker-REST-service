package price.checker.entrypoint;

import java.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import price.checker.service.AVRequestService;
import price.checker.service.StockPriceRpsyService;
import lombok.extern.slf4j.Slf4j;
import java.lang.*;
import java.text.ParseException;


/**
 * This is the controller that passes on user parameters to services to obtain stock prices
 */
@RestController
@Slf4j
public class RequestController {

    //let Spring do its thing and initialize the two services
    @Autowired
    StockPriceRpsyService StockPriceRpsyService;

    @Autowired
    AVRequestService AVRequestService;

    /**
     * This method passes the user input to StockPriceRpsyService to check for data
     * in DB - if not StockPriceRpsyService calls AVRequestService to query Alpha Vantage API
     * and eventually returns desired data back to controller to return to user
     */
    @GetMapping(value = "/prices",
            produces =  MediaType.APPLICATION_JSON_VALUE)
    //we want to take in two parameters - the ticker and the number of days of data to display
    public String prices(
            @RequestParam(value = "stock" , defaultValue="DNKN") String stock,
            @RequestParam(value = "days", defaultValue="5") Integer days)
            throws IOException, ParseException {

        StockPriceRpsyService.saveStockPrice();
        return AVRequestService.getStockData(stock,days);
    }
}