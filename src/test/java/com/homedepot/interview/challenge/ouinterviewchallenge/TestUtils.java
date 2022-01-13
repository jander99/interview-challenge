package com.homedepot.interview.challenge.ouinterviewchallenge;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class TestUtils {

    public static String loadResourceAsString(String fileName) {
        try {
            return new String(Objects.requireNonNull(TestUtils.class
                    .getResourceAsStream(fileName))
                .readAllBytes(),
                StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Unable to read file {}", fileName, e);
            return null;
        }
    }
}
