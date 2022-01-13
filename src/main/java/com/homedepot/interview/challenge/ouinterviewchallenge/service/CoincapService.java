package com.homedepot.interview.challenge.ouinterviewchallenge.service;

import com.homedepot.interview.challenge.ouinterviewchallenge.entity.CoincapAsset;
import com.homedepot.interview.challenge.ouinterviewchallenge.entity.CoincapAssetWrapper;
import com.homedepot.interview.challenge.ouinterviewchallenge.entity.CryptoExchangeRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;

@Service
public class CoincapService {

    private final RestTemplate coincapRestTemplate;

    private final String coincapBaseUrl;
    private final String coincapToken;

    public CoincapService(RestTemplate restTemplate,
                            @Value("${coincap.api.url}") String coincapBaseUrl,
                            @Value("${coincap.api.token}") String coincapToken) {
        this.coincapBaseUrl = coincapBaseUrl;
        this.coincapToken = coincapToken;
        this.coincapRestTemplate = restTemplate;
    }


    public CryptoExchangeRate getCryptoExchangeRate(Double input, String from, String to) {

        try {
            CoincapAsset fromAsset = findTokenFromSymbol(from);
            CoincapAsset toAsset = findTokenFromSymbol(to);

            BigDecimal fromUsdValue = BigDecimal.valueOf(Double.parseDouble(fromAsset.getPriceUsd()));
            BigDecimal toUsdValue = BigDecimal.valueOf(Double.parseDouble(toAsset.getPriceUsd()));

            BigDecimal totalIncomingValue = fromUsdValue.multiply(BigDecimal.valueOf(input));
            BigDecimal numToTokens = totalIncomingValue.divide(toUsdValue, RoundingMode.HALF_UP);

            return CryptoExchangeRate
                    .builder()
                    .fromAmount(input)
                    .toAmount(numToTokens.doubleValue())
                    .fromToken(from)
                    .toToken(to)
                    .build();
        } catch (NoSuchElementException nsee) {
            return null;
        }
    }

    private CoincapAsset findTokenFromSymbol(String tokenSymbol) throws NoSuchElementException{
        String assetSearchUrl = "/v2/assets?search=" + tokenSymbol;
        CoincapAssetWrapper returnValue = coincapRestTemplate
                .exchange(coincapBaseUrl + assetSearchUrl,
                        HttpMethod.GET,
                        getCoincapHeaders(),
                        CoincapAssetWrapper.class)
                .getBody();
        return returnValue.getData()
                .stream().filter(a -> a.getSymbol().equalsIgnoreCase(tokenSymbol))
                .findAny().orElseThrow();
    }

    private HttpEntity<HttpHeaders> getCoincapHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + coincapToken);
        headers.add("Accept-Encoding", "deflate");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
