package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final String API_KEY = "KB7DSCK48G00WIRE";

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    //This is the url that is mapped to this method
    @GetMapping(value = "/prices",
            produces =  MediaType.APPLICATION_JSON_VALUE)
    //we want to take in two parameters - the ticker and the number of days of data to display
    public String prices(
            @RequestParam(value = "ticker" , defaultValue="DNKN") String ticker){
//          @RequestParam(value = "numDays" , defaultValue = "1") Integer numDays){

        //the alpha vantage querying url with ticker is a variable
        final String AlphaVantageUri = "https://www.alphavantage.co/query?" +
                "function=TIME_SERIES_DAILY&" +
                "symbol={ticker}&" +
                "outputsize=full&" +
                "apikey="+API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        //use the restTemplate to submit a GET request with user variables
        return restTemplate.getForObject(AlphaVantageUri, String.class, ticker);
    }
}