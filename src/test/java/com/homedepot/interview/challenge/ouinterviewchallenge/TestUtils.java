package com.homedepot.interview.challenge.ouinterviewchallenge;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class TestUtils {

    public static String loadResourceAsString(String fileName) {
        try {
            return new String(Objects.requireNonNull(TestUtils.class
                    .getResourceAsStream(fileName))
                .readAllBytes(),
                StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }
}
