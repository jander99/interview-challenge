package com.homedepot.interview.challenge.ouinterviewchallenge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "rank",
        "symbol",
        "name",
        "supply",
        "maxSupply",
        "marketCapUsd",
        "volumeUsd24Hr",
        "priceUsd",
        "changePercent24Hr",
        "vwap24Hr",
        "explorer"
})
public class CoincapAsset {

    @JsonProperty("id")
    private String id;
    @JsonProperty("rank")
    private String rank;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("name")
    private String name;
    @JsonProperty("supply")
    private String supply;
    @JsonProperty("maxSupply")
    private Object maxSupply;
    @JsonProperty("marketCapUsd")
    private Object marketCapUsd;
    @JsonProperty("volumeUsd24Hr")
    private Object volumeUsd24Hr;
    @JsonProperty("priceUsd")
    private String priceUsd;
    @JsonProperty("changePercent24Hr")
    private Object changePercent24Hr;
    @JsonProperty("vwap24Hr")
    private Object vwap24Hr;
    @JsonProperty("explorer")
    private String explorer;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}
