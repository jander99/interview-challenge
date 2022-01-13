package com.homedepot.interview.challenge.ouinterviewchallenge.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CryptoExchangeRate {

    Double fromAmount;

    Double toAmount;

    String fromToken;

    String toToken;

}
