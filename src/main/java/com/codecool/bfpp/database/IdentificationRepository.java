package com.codecool.bfpp.database;

import com.codecool.bfpp.data.Identification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IdentificationRepository {
    private final Database database;

    public IdentificationRepository(Database database) {
        this.database = database;
    }

    public Optional<Identification> findOneByUsername(String username) {
        String template = "SELECT username, password FROM identification WHERE username = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(template)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            Optional<Identification> identification = Optional.empty();
            if (resultSet.next()) {
                identification = Optional.of(toEntity(resultSet));
            }
            resultSet.close();
            return identification;
        } catch (SQLException e) {
            System.err.println(e);
            return Optional.empty();
        }
    }

    public List<Identification> findAll() {
        String query = "SELECT username, password FROM identification";

        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            List<Identification> identifications = new ArrayList<>();
            while (resultSet.next()) {
                Identification identification = toEntity(resultSet);
                identifications.add(identification);
            }
            return identifications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Identification toEntity(ResultSet resultSet) throws SQLException {
        return new Identification(
                resultSet.getString("username"),
                resultSet.getString("password")
        );
    }

    // TODO: fixed ??  org.postgresql.util.PSQLException: The column index is out of range: 1, number of columns: 0.
    public void save(Identification identification) {
        if (findOneByUsername(identification.username()).isPresent()) {
            return;
        }

        String template = "INSERT INTO identification (username, password) VALUES (?, ?)";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(template)) {
            prepare(identification, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void prepare(Identification identification, PreparedStatement statement) throws SQLException {
        statement.setString(1, identification.username());
        statement.setString(2, identification.password());
    }
}
