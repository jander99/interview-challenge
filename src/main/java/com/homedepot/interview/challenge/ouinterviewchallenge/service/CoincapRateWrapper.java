package com.homedepot.interview.challenge.ouinterviewchallenge.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.homedepot.interview.challenge.ouinterviewchallenge.entity.CoincapRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "data",
    "timestamp"
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoincapRateWrapper {
  @JsonProperty("data")
  @Builder.Default
  private CoincapRate entity = null;
  @JsonProperty("timestamp")
  private Long timestamp;
  @JsonIgnore
  private Map<String, Object> additionalProperties;
}
