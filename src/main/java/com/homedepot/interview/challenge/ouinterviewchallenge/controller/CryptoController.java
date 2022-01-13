package com.homedepot.interview.challenge.ouinterviewchallenge.controller;

import com.homedepot.interview.challenge.ouinterviewchallenge.entity.CryptoExchangeRate;
import com.homedepot.interview.challenge.ouinterviewchallenge.service.CoincapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api")
public class CryptoController {

    private final CoincapService coincapService;

    public CryptoController(CoincapService coincapService) {
        this.coincapService = coincapService;
    }

    @RequestMapping(value = "/crypto", method = RequestMethod.GET)
    public ResponseEntity<CryptoExchangeRate> getCrypto(@RequestParam(name="amount") Double fromAmount,
                                                        @RequestParam(name="from") String fromToken,
                                                        @RequestParam(name="to") String toToken) {

        //TODO: Implement controller logic based on what is required.

        return null;
    }
}
