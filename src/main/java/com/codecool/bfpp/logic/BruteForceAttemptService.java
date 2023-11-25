package com.codecool.bfpp.logic;

import com.codecool.bfpp.data.BruteForceAttempt;
import com.codecool.bfpp.database.BruteForceAttemptRepository;

import java.util.List;
import java.util.stream.Stream;

public class BruteForceAttemptService {
    private final BruteForceAttemptRepository bruteForceAttemptRepository;
    private final List<BruteForce> variations;

    public BruteForceAttemptService(BruteForceAttemptRepository bruteForceAttemptRepository, List<BruteForce> variations) {
        this.bruteForceAttemptRepository = bruteForceAttemptRepository;
        this.variations = variations;
    }

    public Stream<BruteForceAttempt> attempt(String username) {
        return variations.stream()
                .map(variation -> variation.attempt(username))
                .peek(bruteForceAttemptRepository::save);
    }

    public List<BruteForceAttempt> findAll() {
        return bruteForceAttemptRepository.findAll();
    }
}
