package hello;

import java.io.*;
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
public class RequestController {

    private final String API_KEY = "KB7DSCK48G00WIRE";


    //This is the url that is mapped to this method
    @GetMapping(value = "/prices",
            produces =  MediaType.APPLICATION_JSON_VALUE)
    //we want to take in two parameters - the ticker and the number of days of data to display
    public String prices(
            @RequestParam(value = "stock" , defaultValue="DNKN") String stock,
            @RequestParam(value = "days", defaultValue="5") Integer days)
            throws IOException {


        //the alpha vantage querying url with ticker is a variable
        final String AlphaVantageUri = "https://www.alphavantage.co/query?" +
                "function=TIME_SERIES_DAILY&" +
                "symbol={stock}&" +
                "outputsize=full&" +
                "apikey="+API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        //use the restTemplate to submit a GET request with user variables
        ResponseEntity<String> initialRes = restTemplate.getForEntity(AlphaVantageUri, String.class, stock);

        return getSpecifiedDays(initialRes, days);
    }

    /**
     * This method is used to parse the returned JSON data from Alpha Vantage to only display
     * the stock prices for the number of days specified by the user
     * @param initialRes
     * @param days
     * @return
     * @throws IOException
     */
    public String getSpecifiedDays(ResponseEntity<String> initialRes, Integer days) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> parsed = new LinkedHashMap<>();

        //create a JsonNode as the root
        JsonNode rootNode = new ObjectMapper().readTree(initialRes.getBody());
        //put first chunk of JSON data into the map.
        //this chunk is unique to each query and only shows up once
        parsed.put("Meta Data", rootNode.get("Meta Data").toString());


        //this is the chunk of JSON with all the stock info
        JsonNode timeSeriesStart = rootNode.get("Time Series (Daily)");
        Iterator<String> dates = timeSeriesStart.fieldNames();
        //now we need to loop through and get all the stock prices for the days user specified
        Integer counter = 1;
        while (counter <= days && dates.hasNext()){
            String date = dates.next();
            JsonNode dateNode = timeSeriesStart.get(date);
            parsed.put(date,dateNode.toString());
            counter++;
        }

        String res = objectMapper.writeValueAsString(parsed);
        parsed = null;
        return res;
    }
}