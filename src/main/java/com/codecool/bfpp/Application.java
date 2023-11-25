package com.codecool.bfpp;

import com.codecool.bfpp.database.BruteForceAttemptRepository;
import com.codecool.bfpp.database.Database;
import com.codecool.bfpp.database.IdentificationRepository;
import com.codecool.bfpp.initialize.TableInitializer;
import com.codecool.bfpp.initialize.TableStatements;
import com.codecool.bfpp.logic.BruteForce;
import com.codecool.bfpp.logic.BruteForceAttemptService;
import com.codecool.bfpp.logic.IdentificationService;
import com.codecool.bfpp.logic.LoginService;
import com.codecool.bfpp.ui.BruteForceAttemptFormatter;
import com.codecool.bfpp.ui.BruteForceUi;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Database database = new Database(
                "jdbc:postgresql://localhost:5432/bruteforce_plus_plus",
                "postgres",
                "postgres");
        Map<String, String> tables = Map.of(
                "identification", TableStatements.IDENTIFICATION,
                "brute_force_attempt", TableStatements.BRUTE_FORCE_ATTEMPT
        );
        TableInitializer tableInitializer = new TableInitializer(database, tables);
        tableInitializer.initialize();

        IdentificationRepository identificationRepository = new IdentificationRepository(database);
        BruteForceAttemptRepository bruteForceAttemptRepository = new BruteForceAttemptRepository(database);
        IdentificationService identificationService = new IdentificationService(identificationRepository);
        LoginService loginService = new LoginService(identificationRepository);
        int maximumPasswordLength = 2;
        int maximumNumberOfAttempts = 20000;
        Map<String, String[]> bruteForceTypes = Map.of(
                "Numbers", "1234567890".split(""),
                "Letters Lowercase", "abcdefghijklmnopqrstuvwxyz".split(""),
                "Letters", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""),
                "Letters Numbers", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".split(""),
                "Letters Numbers Special", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$".split("")
        );
        List<BruteForce> bruteForceVariations = bruteForceTypes.entrySet().stream()
                .map(entry -> new BruteForce(entry.getKey(), entry.getValue(), maximumPasswordLength, maximumNumberOfAttempts, loginService))
                .toList();
        BruteForceAttemptService bruteForceAttemptService = new BruteForceAttemptService(bruteForceAttemptRepository, bruteForceVariations);
        Scanner scanner = new Scanner(System.in);
        BruteForceAttemptFormatter bruteForceAttemptFormatter = new BruteForceAttemptFormatter();
        BruteForceUi bruteForceUi = new BruteForceUi(identificationService, bruteForceAttemptService, scanner, bruteForceAttemptFormatter);
        bruteForceUi.run();
    }
}
