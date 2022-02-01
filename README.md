# Interview Challenge # 

## Cryptocurrency Converter ##

This project contains the skeleton of an API that allows the user to convert 
the value of one cryptocurrency into another cryptocurrency. 

## API Specification ##

The following API will be constructed in order to satisfy the request and response shown below

### Request ###

`curl http://localhost:8080/api/crypto?amount={amountFrom}&from={fromToken}&to={toToken}`

A Token is defined as the "symbol" of the cryptocurrency, i.e. Bitcoin = BTC

For example, if the user wants to convert 16.5 bitcoin (BTC) to etherium (ETH) then the request would be  
`curl http://localhost:8080/api/crypto?amount=16.5&from=BTC&to=ETH`

### Response ### 

If Etherium has a 10:1 conversion rate to Bitcoin, the response would be the 3 inputs plus the output 
```json
{
  "fromAmount": 16.5,
  "toAmount": 165.0,
  "fromToken": "BTC",
  "toToken": "ETH"
}
```

In order to get realtime conversion data, a free API available from CoinCap should be used to convert 
from one token to another.  
The documentation for this API is available at https://docs.coincap.io/ 

## Running the code ##

This is a standard gradle project

## Hints ## 

- There is a barebones controller and service. This is provided since the Tests provided need this minimum code.  
- For each of our API requests, we might need to make several requests to Coincap.  
- Look at Tests. All tests should pass. 
- You can use Google just like you would in your day-to-day job. 

