package name.webdizz.cdc.stock.catalog.client;

import org.apache.http.conn.ssl.NoopHostnameVerifier;

import feign.Client;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;

public class ClientFactory {

    private static Client.Default client = new Client.Default(new TrustingSSLSocketFactory(), new NoopHostnameVerifier());

    public static StockPriceClient stockPriceClient(String url) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger())
                .client(client)
                .target(StockPriceClient.class, url);
    }

}
