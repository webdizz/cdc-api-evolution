package name.webdizz.cdc.stock.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import name.webdizz.cdc.stock.catalog.client.ClientFactory;
import name.webdizz.cdc.stock.catalog.client.StockPriceClient;
import name.webdizz.cdc.stock.catalog.client.StockPriceClient.StockPrice;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class StockCatalogController {

    private final StockPriceClient stockPriceClient;

    public StockCatalogController(@Value("${service.stock.price.url}") String url) {
        stockPriceClient = ClientFactory.stockPriceClient(url);
    }

    @RequestMapping("/stock/catalog/{party}")
    public StockCatalog stockPriceFor(@PathVariable("party") String party) {
        StockPrice stockPrice = stockPriceClient.stockPriceFor(party);
        return new StockCatalog(party, stockPrice);
    }

    @Data
    @AllArgsConstructor
    class StockCatalog {
        private String party;
        private StockPrice stockPrice;
    }
}
