package com.homedepot.interview.challenge.ouinterviewchallenge.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.homedepot.interview.challenge.ouinterviewchallenge.entity.CoincapAsset;
import com.homedepot.interview.challenge.ouinterviewchallenge.entity.CoincapAssetWrapper;
import com.homedepot.interview.challenge.ouinterviewchallenge.entity.CryptoExchangeRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class CoincapService {

    private final RestTemplate coincapRestTemplate;

    private final String coincapBaseUrl;
    private final String coincapToken;

    public CoincapService(@Value("${coincap.api.url}") String coincapBaseUrl,
                          @Value("${coincap.api.token}") String coincapToken) {
        this.coincapBaseUrl = coincapBaseUrl;
        this.coincapToken = coincapToken;
        this.coincapRestTemplate = new RestTemplate();
    }


    public CryptoExchangeRate getCryptoExchangeRate(Double input, String from, String to) {

        try {
            CoincapAsset fromAsset = findTokenFromSymbol(from).orElseThrow();
            CoincapAsset toAsset = findTokenFromSymbol(to).orElseThrow();

            log.info("From Asset {}", fromAsset);
            log.info("To Asset {}", toAsset);

            BigDecimal fromUsdValue = BigDecimal.valueOf(Double.parseDouble(fromAsset.getPriceUsd()));
            BigDecimal toUsdValue = BigDecimal.valueOf(Double.parseDouble(toAsset.getPriceUsd()));

            BigDecimal totalIncomingValue = fromUsdValue.multiply(BigDecimal.valueOf(input));
            BigDecimal numToTokens = totalIncomingValue.divide(toUsdValue, RoundingMode.HALF_UP);

            CryptoExchangeRate computedRate = CryptoExchangeRate
                    .builder()
                    .fromAmount(input)
                    .toAmount(numToTokens.doubleValue())
                    .fromToken(from)
                    .toToken(to)
                    .build();
            return computedRate;
        } catch (NoSuchElementException nsee) {
            log.error(nsee.getMessage());
            return null;
        }
    }

    private Optional<CoincapAsset> findTokenFromSymbol(String tokenSymbol) {
        String assetSearchUrl = "/v2/assets?search=" + tokenSymbol;
        CoincapAssetWrapper returnValue = coincapRestTemplate
                .exchange(coincapBaseUrl + assetSearchUrl,
                        HttpMethod.GET,
                        getCoincapHeaders(),
                        CoincapAssetWrapper.class)
                .getBody();
        assert returnValue != null;
        return returnValue.getData()
                .stream().filter(a -> a.getSymbol().equalsIgnoreCase(tokenSymbol))
                .findAny();
    }

    private HttpEntity<HttpHeaders> getCoincapHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + coincapToken);
        headers.add("Accept-Encoding", "deflate");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
