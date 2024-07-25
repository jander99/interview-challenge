package com.homedepot.interview.challenge.ouinterviewchallenge.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "currencySymbol",
    "id",
    "rateUsd",
    "symbol",
    "type"
})
public class CoincapRate {
  @JsonProperty("currencySymbol")
  private String currencySymbol;
  @JsonProperty("id")
  private String id;
  @JsonProperty("rateUsd")
  private Double rateUsd;
  @JsonProperty("symbol")
  private String symbol;
  @JsonProperty("type")
  private String type;
}
