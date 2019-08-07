package price.checker.entrypoint;

import java.io.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import price.checker.service.AVRequestService;
import java.lang.*;


/**
 * This is the controller that passes on user parameters to services to obtain stock prices
 */
@RestController
public class RequestController {

    //This is the url that is mapped to this method
    @GetMapping(value = "/prices",
            produces =  MediaType.APPLICATION_JSON_VALUE)
    //we want to take in two parameters - the ticker and the number of days of data to display
    public String prices(
            @RequestParam(value = "stock" , defaultValue="DNKN") String stock,
            @RequestParam(value = "days", defaultValue="5") Integer days)
            throws IOException {

        return new AVRequestService().getStockData(stock,days);
    }
}