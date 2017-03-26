package name.webdizz.cdc.stock.price

import au.com.dius.pact.provider.junit.PactRunner
import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.State
import au.com.dius.pact.provider.junit.loader.PactFolder
import au.com.dius.pact.provider.junit.target.HttpTarget
import au.com.dius.pact.provider.junit.target.Target
import au.com.dius.pact.provider.junit.target.TestTarget
import groovy.util.logging.Slf4j
import org.junit.runner.RunWith
import spock.lang.Specification

@RunWith(PactRunner)
@Provider('StockPrice')
@PactFolder('../consumer/target/pacts')
@Slf4j
class StockPriceProviderTest extends Specification {

    @TestTarget
    public static final Target provider = new HttpTarget('http', 'localhost', 8080, '/', true)

    @State('StockPrice is Available')
    void stockPriceIsAvailable() {
        log.info('Now service is in default state')
    }
}