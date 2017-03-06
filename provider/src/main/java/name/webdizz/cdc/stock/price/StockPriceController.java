package name.webdizz.cdc.stock.price;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import lombok.Value;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class StockPriceController {

    private static final Map<String, StockPrice> stockPrices = ImmutableMap.of(
            "NYSE:EPAM", new StockPrice("NYSE:EPAM", 73.32, new StockChange(0.74, 1.02)),
            "NYSE:GOOG", new StockPrice("NYSE:GOOG", 819.67, new StockChange(3.74, 2.02))
    );

    @RequestMapping("/stock/price/{party}")
    public StockPrice stockPriceFor(@PathVariable("party") String party) {
        return stockPrices.get(party);
    }

    @Value
    static class StockPrice {
        private String party;
        private double price;
        private StockChange change;

    }

    @Value
    static class StockChange {
        private double abs;
        private double rel;
    }
}
