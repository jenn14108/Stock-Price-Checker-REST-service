package hello;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;
import java.util.*;
import java.lang.*;



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
            @RequestParam(value = "stock" , defaultValue="DNKN") String stock,
            @RequestParam(value = "days", defaultValue="5") Integer days) throws IOException {


        //the alpha vantage querying url with ticker is a variable
        final String AlphaVantageUri = "https://www.alphavantage.co/query?" +
                "function=TIME_SERIES_DAILY&" +
                "symbol={stock}&" +
                "outputsize=full&" +
                "apikey="+API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        //use the restTemplate to submit a GET request with user variables
        ResponseEntity<String> initialRes = restTemplate.getForEntity(AlphaVantageUri, String.class, stock);

        //create a JsonNode as the root
        JsonNode rootNode = new ObjectMapper().readTree(initialRes.getBody());
        StringBuilder prices = new StringBuilder();

        JsonNode metaDataParent = rootNode.path("Meta Data");
        prices.append(metaDataParent.path("2. Symbol").asText()+"\n");

        //go into the main chunk of JSON containing all the dates
        JsonNode timeSeriesStart = rootNode.get("Time Series (Daily)");
        //create an iterator for all dates
        Iterator<String> dates = timeSeriesStart.fieldNames();
        Integer counter = 1;
        while(counter <= days && dates.hasNext()){
            String date = dates.next();
            JsonNode fieldValue = timeSeriesStart.get(date);
            prices.append("\n" + date + " :\n");
            prices.append("Open: " + fieldValue.path("1. open").asText()+"\n");
            prices.append("High: " + fieldValue.path("2. high").asText()+"\n");
            prices.append("Low: " + fieldValue.path("3. low").asText()+"\n");
            prices.append("Close: " + fieldValue.path("4. close").asText()+"\n");
            prices.append("Volume: " + fieldValue.path("5. volume").asText()+"\n");

            counter++;
        }

        //return initialRes.getBody();
        return prices.toString();






    }
}