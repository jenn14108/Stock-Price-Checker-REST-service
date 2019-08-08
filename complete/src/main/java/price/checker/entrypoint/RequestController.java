package price.checker.entrypoint;

import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import price.checker.domain.StockPrice;
import price.checker.domain.rpsy.StockPriceRepository;
import price.checker.service.AVRequestService;
import price.checker.service.StockPriceRpsyService;
import net.sf.aspect4log.Log;
import lombok.extern.slf4j.Slf4j;
import java.lang.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This is the controller that passes on user parameters to services to obtain stock prices
 */
@RestController
@Log
@Slf4j
public class RequestController {

    @Autowired
    StockPriceRpsyService StockPriceRpsyService;

    @Autowired
    AVRequestService AVRequestService;

    //This is the url that is mapped to this method
    @GetMapping(value = "/prices",
            produces =  MediaType.APPLICATION_JSON_VALUE)
    @Log
    //we want to take in two parameters - the ticker and the number of days of data to display
    public String prices(
            @RequestParam(value = "stock" , defaultValue="DNKN") String stock,
            @RequestParam(value = "days", defaultValue="5") Integer days)
            throws IOException, ParseException {

        StockPriceRpsyService.saveStockPrice();
        return AVRequestService.getStockData(stock,days);
    }
}