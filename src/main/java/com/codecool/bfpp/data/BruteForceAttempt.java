package com.codecool.bfpp.data;

import java.time.Duration;

public record BruteForceAttempt(
        String type,
        String username,
        String password,
        int tries,
        Duration duration) {
}
