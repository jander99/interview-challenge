package com.homedepot.interview.challenge.ouinterviewchallenge;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OuInterviewChallengeApplication {

    @Generated
    public static void main(String[] args) {
        SpringApplication.run(OuInterviewChallengeApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
