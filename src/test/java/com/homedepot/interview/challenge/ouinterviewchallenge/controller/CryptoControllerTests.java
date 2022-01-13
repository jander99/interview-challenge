package com.homedepot.interview.challenge.ouinterviewchallenge.controller;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest
public class CryptoControllerTests {

    @Test
    void test_1(WireMockRuntimeInfo wireMockRuntimeInfo) {
        stubFor(get("/v2/assets?search=BTC")
                .willReturn(ok().withBody("foo")));

        stubFor(get("/v2/assets?search=DOGE")
                .willReturn(ok().withBody("bar")));

        

    }

}
