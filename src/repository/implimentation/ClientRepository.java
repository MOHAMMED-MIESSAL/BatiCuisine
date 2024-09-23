package repository.implimentation;

import config.dbConnection;
import model.Client;
import repository.interfaces.ClientRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements ClientRepositoryInterface {

    private final Connection connection;

    public ClientRepository() throws SQLException {
        this.connection = dbConnection.getInstance().getConnection();
    }

    @Override
    public void addClient(Client client) {
        String query = "INSERT INTO client (name, address, phone, is_professional) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setClientFields(statement, client);
            statement.executeUpdate();
        } catch (SQLException e) {
            logError("Error adding client", e);
        }
    }

    @Override
    public Client getClientById(int id) {
        String query = "SELECT * FROM client WHERE id = ?";
        Client client = null;
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                client = buildClientFromResultSet(resultSet);
            } else {
                System.out.println("No client found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error fetching client with id: " + id, e);
        }
        return client;
    }

    @Override
    public List<Client> getAllClients() {
        String query = "SELECT * FROM client";
        List<Client> clientList = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                clientList.add(buildClientFromResultSet(resultSet));
            }

            if (clientList.isEmpty()) {
                System.out.println("No clients found.");
            }

        } catch (SQLException e) {
            logError("Error fetching all clients", e);
        }

        return clientList;
    }

    @Override
    public void updateClient(int id, Client client) {
        String query = "UPDATE client SET name = ?, address = ?, phone = ?, is_professional = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setClientFields(statement, client);
            statement.setInt(5, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No client found with id: " + id);
            }

        } catch (SQLException e) {
            logError("Error updating client with id: " + id, e);
        }
    }

    @Override
    public void deleteClient(int id) {
        String query = "DELETE FROM client WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No client found with id: " + id);
            }

        } catch (SQLException e) {
            logError("Error deleting client with id: " + id, e);
        }
    }

    public Client getClientByName(String name) {
        String query = "SELECT * FROM client WHERE name = ? ";
        Client client = null;
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                client = buildClientFromResultSet(resultSet);
            }
//            else {
//                System.out.println("No client found with name: " + name);
//            }
        } catch (SQLException e) {
            logError("Error fetching client with name: " + name, e);
        }
        return client;

    }

    // Helper method to log errors
    private void logError(String message, SQLException e) {
        System.err.println(message + ": " + e.getMessage());
        // Optional: Use a logging framework like Log4j or SLF4J instead of System.err
    }

    // Helper method to set the client fields in a PreparedStatement
    private void setClientFields(PreparedStatement statement, Client client) throws SQLException {
        statement.setString(1, client.getName());
        statement.setString(2, client.getAddress());
        statement.setString(3, client.getPhone());
        statement.setBoolean(4, client.isIs_professional());
    }

    // Helper method to build a Client object from a ResultSet
    private Client buildClientFromResultSet(ResultSet resultSet) throws SQLException {
        return new Client(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("address"),
                resultSet.getString("phone"),
                resultSet.getBoolean("is_professional")
        );
    }
}
