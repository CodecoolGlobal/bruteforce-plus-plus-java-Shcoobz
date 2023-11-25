package com.codecool.bfpp.logic;

import com.codecool.bfpp.data.BruteForceAttempt;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class BruteForce {
    private final String type;
    private final String[] characters;
    private final int maximumPasswordLength;
    private final int maximumNumberOfAttempts;
    private final LoginService loginService;

    public BruteForce(String type, String[] characters, int maximumPasswordLength, int maximumNumberOfAttempts, LoginService loginService) {
        this.type = type;
        this.characters = characters;
        this.maximumPasswordLength = maximumPasswordLength;
        this.maximumNumberOfAttempts = maximumNumberOfAttempts;
        this.loginService = loginService;
    }

    public BruteForceAttempt attempt(String username) {
        LocalTime start = LocalTime.now();
        AtomicInteger attempts = new AtomicInteger(0);
        AtomicReference<String> password = new AtomicReference<>(null);
        attempt(username, "", attempts, password);
        LocalTime end = LocalTime.now();
        return new BruteForceAttempt(
                type,
                username,
                password.get(),
                attempts.get(),
                Duration.between(start, end)
        );
    }

    private void attempt(String username, String permutation, AtomicInteger attempts, AtomicReference<String> password) {
        if (isInvalidAttempt(permutation, attempts, password)) {
            return;
        }
        if (isLoginSuccessful(username, permutation, attempts)) {
            password.set(permutation);
            return;
        }
        for (String character : characters) {
            attempt(username, permutation + character, attempts, password);
        }
    }

    private boolean isInvalidAttempt(String permutation, AtomicInteger attempts, AtomicReference<String> password) {
        return permutation.length() > maximumPasswordLength ||
                attempts.get() > maximumNumberOfAttempts ||
                Objects.nonNull(password.get());
    }

    private boolean isLoginSuccessful(String username, String password, AtomicInteger attempts) {
        attempts.incrementAndGet();
        return loginService.login(username, password);
    }
}
