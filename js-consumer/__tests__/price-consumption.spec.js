'use strict'

const path = require('path')
const Pact = require('pact')
const getStockPrice = require('../stock-catalog').getStockPrice

describe("Stock API", () => {
  let url = 'http://localhost'

  const port = 8989
  const provider = Pact({
    port: port,
    log: path.resolve(process.cwd(), 'logs', 'mockserver-integration.log'),
    dir: path.resolve(process.cwd(), 'pacts'),
    spec: 2,
    consumer: 'StockCatalogJS',
    provider: 'StockPrice'
  })

  // matcher aliases
  const like = Pact.Matchers.somethingLike

  beforeAll(() => provider.setup())

  afterAll(() => provider.finalize())

  describe("Stock Price service works", () => {
    beforeAll(done => {
      const interaction = {
        state: 'Stock Price Service is available',
        uponReceiving: 'Stock Price Request',
        withRequest: {
          method: 'GET',
          path: '/stock/price/NYSE:EPAM'
        },
        willRespondWith: {
          status: 200,
          body: {
            'party': like('NYSE:EPAM'),
            'price': like(73.32),
            'change': {
              'abs': like(0.74),
              'rel': like(1.02)
            }
          }
        }
      }
      provider.addInteraction(interaction).then(done, done)
    })
    // mock response
    const stockPriceMock = {
      "party": "NYSE:EPAM",
      "price": 73.32,
      "change": {
        "abs": 0.74,
        "rel": 1.02
        }
    }
    // add expectations
    it('returns a sucessful body', done => {
      return getStockPrice({ url, port }, 'NYSE:EPAM')
        .then(response => {
          expect(response.data).toEqual(stockPriceMock)
          expect(response.status).toEqual(200)
          done()
        })
    })

    // verify with Pact
    it('successfully verifies contract', () => provider.verify())
  })
})
