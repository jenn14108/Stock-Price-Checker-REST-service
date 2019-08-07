package price.checker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import net.sf.aspect4log.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is used to query to Alpha Vantage for stock data
 */
@RequiredArgsConstructor
@Slf4j
@Log
@Validated
@Service
public class AVRequestService {

    private final String API_KEY = "KB7DSCK48G00WIRE";

    public String getStockData (String stock, Integer days) throws IOException{
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
    public static String getSpecifiedDays(ResponseEntity<String> initialRes, Integer days) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> parsed = new LinkedHashMap<>();

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
        while (counter <= days && dates.hasNext()) {
            String date = dates.next();
            JsonNode dateNode = timeSeriesStart.get(date);
            parsed.put(date, dateNode.toString());
            counter++;
        }

        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(parsed)
                .replaceAll("\\\\", "");
    }
}
