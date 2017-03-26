package name.webdizz.cdc.stock.price

import au.com.dius.pact.consumer.Pact
import au.com.dius.pact.consumer.PactProviderRule
import au.com.dius.pact.consumer.PactVerification
import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.model.PactFragment
import au.com.dius.pact.model.PactSpecVersion
import name.webdizz.cdc.stock.catalog.client.ClientFactory
import org.junit.Rule
import spock.lang.Specification

class StockPriceConsumerTest extends Specification {

    static final String PROVIDER_NAME = 'StockPrice'

    @Rule
    private PactProviderRule provider = new PactProviderRule(StockPriceConsumerTest.PROVIDER_NAME, 'localhost', 8080, false, PactSpecVersion.V3, this)


    @Pact(provider = StockPriceConsumerTest.PROVIDER_NAME, consumer = 'StockCatalog')
    PactFragment createLoginFragment(PactDslWithProvider builder) {
        builder
            .given('StockPrice is Available')
                .uponReceiving("Stock Price Request")
                .headers(['Content-Type': 'application/json'])
                .matchPath('/stock/price/[A-Z\\:]+', '/stock/price/NYSE:EPAM')
                .method('GET')
            .willRespondWith()
                .status(200)
                .headers(['Content-Type': 'application/json'])
                .body(new PactDslJsonBody()
                    .decimalType('price')
                    .object('change')
                        .decimalType('abs')
                        .decimalType('rel')
                )
            .toFragment()
    }

    @PactVerification(StockPriceConsumerTest.PROVIDER_NAME)
    def 'ensure stock price is working'() {
        when: 'there is stock price request'
        def stockPrice = ClientFactory.stockPriceClient(provider.getConfig().url())
                .stockPriceFor('NYSE:EPAM')

        then: 'stock price returned'
        stockPrice != null
        stockPrice.price > 0
        stockPrice.change != null
        stockPrice.change.abs > 0
        stockPrice.change.rel > 0
    }

}