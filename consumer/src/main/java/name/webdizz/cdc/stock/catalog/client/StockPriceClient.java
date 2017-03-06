package name.webdizz.cdc.stock.catalog.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import lombok.Data;

@Headers("Accept: application/json")
public interface StockPriceClient {

    @RequestLine("GET /stock/price/{party}")
    @Headers({ "Content-Type: application/json" })
    StockPrice stockPriceFor(@Param("party") String party);

    @Data
    class StockPrice {
        private double price;
        private StockChange change;

    }

    @Data
    class StockChange {
        private double abs;
        private double rel;
    }
}
