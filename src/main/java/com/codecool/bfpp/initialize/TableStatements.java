package com.codecool.bfpp.initialize;

public interface TableStatements {
    String IDENTIFICATION =
        "CREATE TABLE IF NOT EXISTS identification (" +
            "id SERIAL PRIMARY KEY, " +
            "username VARCHAR(255) NOT NULL, " +
            "password VARCHAR(255) NOT NULL);";

    String BRUTE_FORCE_ATTEMPT =
        "CREATE TABLE IF NOT EXISTS brute_force_attempt (" +
            "id SERIAL PRIMARY KEY, " +
            "type VARCHAR(50), " +
            "username VARCHAR(255), " +
            "password VARCHAR(255), " +
            "tries INT, " +
            "duration INT);";
}
