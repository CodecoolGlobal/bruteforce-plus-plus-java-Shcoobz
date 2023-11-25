package com.codecool.bfpp.ui;

import com.codecool.bfpp.data.BruteForceAttempt;

public class BruteForceAttemptFormatter {
    public String format(BruteForceAttempt attempt) {
        return """
                %25s | %7s | %20s | %20s | %d tries \t| %s""".formatted(
                attempt.type(),
                attempt.password() != null ? "Success" : "Failed",
                attempt.username(),
                attempt.password() != null ? attempt.password() : "---",
                attempt.tries(),
                "%02d minutes %02d seconds".formatted(
                        attempt.duration().toMinutesPart(), attempt.duration().toSecondsPart())
        );
    }
}
