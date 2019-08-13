package entrypoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import price.checker.service.AVRequestService;
import price.checker.service.StockPriceRpsyService;

public class RequestControllerTest {

    @MockBean
    private StockPriceRpsyService StockPriceRpsyService;

    @MockBean
    private AVRequestService AVRequestService;



  @Test
  public void getPrices() {}
}