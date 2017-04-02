'use strict'

const axios = require('axios')

exports.getStockPrice = (endpoint, party) => {
  const url = endpoint.url
  const port = endpoint.port

  return axios.request({
    method: 'GET',
    baseURL: `${url}:${port}`,
    url: `/stock/price/${party}`,
    headers: { 'Accept': 'application/json' }
  })
}
