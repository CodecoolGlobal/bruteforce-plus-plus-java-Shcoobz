package com.codecool.bfpp.ui;

import com.codecool.bfpp.logic.BruteForceAttemptService;
import com.codecool.bfpp.logic.IdentificationService;

import java.util.List;
import java.util.Scanner;

public class BruteForceUi {
    private final IdentificationService identificationService;
    private final BruteForceAttemptService bruteForceAttemptService;
    private final Scanner scanner;
    private final BruteForceAttemptFormatter bruteForceAttemptFormatter;

    public BruteForceUi(IdentificationService identificationService, BruteForceAttemptService bruteForceAttemptService, Scanner scanner, BruteForceAttemptFormatter bruteForceAttemptFormatter) {
        this.identificationService = identificationService;
        this.bruteForceAttemptService = bruteForceAttemptService;
        this.scanner = scanner;
        this.bruteForceAttemptFormatter = bruteForceAttemptFormatter;
    }

    public void run() {
        while (true) {
            displayMainMenu();
            handleChoice();
        }
    }

    private void displayMainMenu() {
        System.out.println("\n----- Bruteforce++ -----");
        System.out.println("\nAvailable identification names: ");
        List<String> usernames = identificationService.findAllUsernames();
        if (usernames.isEmpty()) {
            System.out.println("-- Empty --");
        } else {
            usernames.forEach(System.out::println);
        }
        System.out.println("\nOptions: ");
        System.out.println("1. Add one identification.");
        System.out.println("2. Bruteforce one identification.");
        System.out.println("3. Display bruteforce attempts.");
        System.out.println("4. Exit.");
    }

    private void handleChoice() {
        System.out.print("Choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> addOneIdentification();
            case 2 -> bruteforceOneIdentification();
            case 3 -> displayBruteforceAttempts();
            case 4 -> System.exit(0);
        }
    }

    private void addOneIdentification() {
        System.out.println("\n*** Add one identification ***");
        System.out.print("Username: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();
        identificationService.save(username, password);
        System.out.println("Identification saved.");
    }

    private void bruteforceOneIdentification() {
        System.out.println("\n*** Bruteforce one identification ***");
        System.out.print("Username: ");
        String username = scanner.next();
        if (!identificationService.exists(username)) {
            System.out.println("Identification does not exist: " + username);
            return;
        }
        System.out.println("Attempting to bruteforce:");
        bruteForceAttemptService.attempt(username)
                .map(bruteForceAttemptFormatter::format)
                .forEach(System.out::println);
    }

    private void displayBruteforceAttempts() {
        System.out.println("\n*** Display bruteforce attempts");
        bruteForceAttemptService.findAll().stream()
                .map(bruteForceAttemptFormatter::format)
                .forEach(System.out::println);
    }
}
