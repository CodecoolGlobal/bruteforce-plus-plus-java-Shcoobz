package com.codecool.bfpp.database;

import com.codecool.bfpp.data.BruteForceAttempt;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BruteForceAttemptRepository {
    private final Database database;

    public BruteForceAttemptRepository(Database database) {
        this.database = database;
    }

    public List<BruteForceAttempt> findAll() {
        // Write the select statements here!
        String template = "";
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(template)) {
            List<BruteForceAttempt> attempts = new ArrayList<>();
            while (resultSet.next()) {
                BruteForceAttempt attempt = toEntity(resultSet);
                attempts.add(attempt);
            }
            return attempts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private BruteForceAttempt toEntity(ResultSet resultSet) throws SQLException {
        return new BruteForceAttempt(
                resultSet.getString("type"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getInt("tries"),
                Duration.ofMillis(resultSet.getInt("duration"))
        );
    }

    public void save(BruteForceAttempt bruteForceAttempt) {
        // Write the insert statements here!
        String template = "";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(template)) {
            prepare(bruteForceAttempt, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void prepare(BruteForceAttempt bruteForceAttempt, PreparedStatement statement) throws SQLException {
        statement.setString(1, bruteForceAttempt.type());
        statement.setString(2, bruteForceAttempt.username());
        statement.setString(3, bruteForceAttempt.password());
        statement.setInt(4, bruteForceAttempt.tries());
        statement.setLong(5, bruteForceAttempt.duration().toMillis());
    }
}
