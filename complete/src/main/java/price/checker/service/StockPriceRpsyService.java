package price.checker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import price.checker.domain.rpsy.StockPriceRepository;
import price.checker.domain.StockPrice;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This is the service responsible for updating, deleting, retrieving
 * and adding new data entries into mySQL table "StockPrice"
 */

@Slf4j
@Service
public class StockPriceRpsyService {

    @Autowired
    private StockPriceRepository stockPriceRepository;

    private final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * This is the method that is used to check inside our database
     * to see if we have all the data wanted by the user available
     * 1 = yes
     * 0 = no
     * @param symbol
     */
    public Integer checkForWantedPrices(String symbol)throws ParseException, IOException {

        return stockPriceRepository.findMostRecent(symbol);
    }

    /**
     * Simple method that calls on the repository bean to query into db
     * @param symbol
     * @param days
     * @return
     */
    public List<StockPrice> retrievePrices(String symbol, Integer days){
        return stockPriceRepository.findAllMatches(symbol, days);
    }

    /**
     * This method is used to parse the returned JSON data from Alpha Vantage to only display
     * the stock prices for the number of days specified by the user
     * @param initialRes
     * @param days
     * @return
     * @throws IOException
     */
    public void saveAVPrices(String symbol, ResponseEntity<String> initialRes, Integer days)
                                throws IOException, ParseException {

        //create a List to store all Stock Price objects for batch save
        List<StockPrice> prices = new ArrayList<>();
        //create a JsonNode as the root
        JsonNode rootNode = new ObjectMapper().readTree(initialRes.getBody());


        //this is the chunk of JSON with all the stock info
        JsonNode timeSeriesStart = rootNode.get("Time Series (Daily)");
        Iterator<String> dates = timeSeriesStart.fieldNames();
        //now we need to loop through and get all the stock prices and save into db
        while (dates.hasNext()) {
            String dateString = dates.next();
            JsonNode dateNode = timeSeriesStart.get(dateString);
            StockPrice spObj = new StockPrice(symbol, DF.parse(dateString),
                    Double.parseDouble(dateNode.findValue("1. open").asText()),
                    Double.parseDouble(dateNode.findValue("2. high").asText()),
                    Double.parseDouble(dateNode.findValue("3. low").asText()),
                    Double.parseDouble(dateNode.findValue("4. close").asText()),
                    Integer.parseInt(dateNode.findValue("5. volume").asText()));
            prices.add(spObj);
            //log.info("We have successfully saved new object {} of {} into db",spObj.getSymbol(), spObj.getDate());
        }
        stockPriceRepository.saveAll(prices);
        log.info("Saved all new prices");
    }
}
