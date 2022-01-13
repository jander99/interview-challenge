package com.homedepot.interview.challenge.ouinterviewchallenge.service;

import com.homedepot.interview.challenge.ouinterviewchallenge.entity.CoincapAsset;
import com.homedepot.interview.challenge.ouinterviewchallenge.entity.CoincapAssetWrapper;
import com.homedepot.interview.challenge.ouinterviewchallenge.entity.CryptoExchangeRate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CoincapServiceTests {


    private static RestTemplate mockRestTemplate;

    private static CoincapService serviceUnderTest;

    @BeforeAll
    static void setup() {
        mockRestTemplate = mock(RestTemplate.class);
        serviceUnderTest = new CoincapService(mockRestTemplate, "https://foo", "bar");
    }

    @Test
    public void test_25_BTC_DOGE_Conversion() {

        CoincapAsset btcAsset = new CoincapAsset();
        btcAsset.setSymbol("BTC");
        btcAsset.setPriceUsd("25.00");
        CoincapAssetWrapper btcWrapper = CoincapAssetWrapper
            .builder()
            .data(new ArrayList<>(){{add(btcAsset);}})
            .build();

        CoincapAsset dogeAsset = new CoincapAsset();
        dogeAsset.setSymbol("DOGE");
        dogeAsset.setPriceUsd("0.005");
        CoincapAssetWrapper dogeWrapper = CoincapAssetWrapper
            .builder()
            .data(new ArrayList<>(){{add(dogeAsset);}})
            .build();


        when(mockRestTemplate.exchange(eq("https://foo/v2/assets?search=BTC"),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(CoincapAssetWrapper.class)))
            .thenReturn(ResponseEntity.ok(btcWrapper));

        when(mockRestTemplate.exchange(eq("https://foo/v2/assets?search=DOGE"),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(CoincapAssetWrapper.class)))
            .thenReturn(ResponseEntity.ok(dogeWrapper));

        CryptoExchangeRate expectedExchangeRate = CryptoExchangeRate
            .builder()
            .fromAmount(1500.00)
            .toAmount(7500000.00)
            .fromToken("BTC")
            .toToken("DOGE")
            .build();

        CryptoExchangeRate actual = serviceUnderTest.getCryptoExchangeRate(1500.00, "BTC", "DOGE");

        assertThat(actual.getToAmount()).isEqualTo(expectedExchangeRate.getToAmount());
    }

    @Test
    public void test_No_Asset_From_Coincap() {

        CoincapAsset ethAsset = new CoincapAsset();
        ethAsset.setSymbol("ETH");
        ethAsset.setPriceUsd("15.00");
        CoincapAssetWrapper ethWrapper = CoincapAssetWrapper
            .builder()
            .data(new ArrayList<>(){{add(ethAsset);}})
            .build();

        when(mockRestTemplate.exchange(eq("https://foo/v2/assets?search=BTC"),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(CoincapAssetWrapper.class)))
            .thenReturn(ResponseEntity.ok(ethWrapper));

        assertNull(serviceUnderTest.getCryptoExchangeRate(100., "BTC", "DOGE"));
    }
}
