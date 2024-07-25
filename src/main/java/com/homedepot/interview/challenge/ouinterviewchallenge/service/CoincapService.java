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


    public CryptoExchangeRate getCryptoExchangeRate(Double input, String from, String to) throws NoSuchElementException {
        double fromPrice;
        double toPrice;

        fromPrice = Double.parseDouble(getAsset(from).getPriceUsd());
        toPrice = Double.parseDouble(getAsset(to).getPriceUsd());
        var exchangeRate = fromPrice / toPrice;

        return CryptoExchangeRate.builder()
            .fromAmount(input)
            .toAmount(input * exchangeRate)
            .fromToken(from)
            .toToken(to)
            .build();
    }


    /**
     * Searches the Coincap API for a token by Symbol name
     */
    private CoincapAsset getAsset(String tokenSymbol) throws NoSuchElementException {

        String assetSearchUrl = "/v2/assets?search=" + tokenSymbol;
        CoincapAssetWrapper returnValue = coincapRestTemplate
            .exchange(coincapBaseUrl + assetSearchUrl,
                HttpMethod.GET,
                getCoincapHeaders(),
                CoincapAssetWrapper.class)
            .getBody();

        if (returnValue == null) {
            throw new NoSuchElementException("No asset found for symbol: " + tokenSymbol);
        }

        return returnValue
            .getData()
            .stream()
            .filter(a -> a.getSymbol().equalsIgnoreCase(tokenSymbol))
            .findAny()
            .orElseThrow();
    }

    /**
     *  Used for Authorization to Coincap since an API key is required.
     *
     */
    private HttpEntity<HttpHeaders> getCoincapHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + coincapToken);
        headers.add("Accept-Encoding", "deflate");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
