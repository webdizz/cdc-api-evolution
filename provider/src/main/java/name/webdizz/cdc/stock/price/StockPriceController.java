package name.webdizz.cdc.stock.price;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import lombok.Value;

import static name.webdizz.cdc.stock.price.StockPriceController.Level.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class StockPriceController {

    private static final StockMarketCap EPAM_STOCK_MARKET_CAP = new StockMarketCap(3.71, B);
    private static final StockMarketCap GOOG_STOCK_MARKET_CAP = new StockMarketCap(568.05, B);

    private static final StockChange EPAM_STOCK_CHANGE = new StockChange(0.74, 1.02);
    private static final StockChange GOOG_STOCK_CHANGE = new StockChange(3.74, 2.02);

    private static final Map<String, StockPrice> stockPrices = ImmutableMap.of(
            "NYSE:EPAM", new StockPrice("NYSE:EPAM", 73.32, EPAM_STOCK_CHANGE),
            "NASDAQ:GOOG", new StockPrice("NASDAQ:GOOG", 819.67, GOOG_STOCK_CHANGE)
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

    @Value
    static class StockMarketCap {
        private double value;
        private Level level;
    }

    enum Level {
        B
    }
}
