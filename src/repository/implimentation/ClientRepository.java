package repository.implimentation;

import config.dbConnection;
import model.Client;
import repository.interfaces.ClientRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements ClientRepositoryInterface {

    private Connection connection;

    public ClientRepository() {
        try {
            this.connection = dbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addClient(Client client) {
        String query = "INSERT INTO client (name, address, phone, is_professional) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getAddress());
            statement.setString(3, client.getPhone());
            statement.setBoolean(4, client.isIs_professional());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client getClientById(int id) {
        String query = "SELECT * FROM client WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("is_professional")
                );
            }else {
                System.out.println("No Client found with id: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clientList = new ArrayList<>();
        String query = "SELECT * FROM client";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (!resultSet.next()) {
                System.out.println("No data found");
            } else {
                do {
                    Client client = new Client(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("address"),
                            resultSet.getString("phone"),
                            resultSet.getBoolean("is_professional")
                    );
                    clientList.add(client);
                } while (resultSet.next());
            }

        } catch (SQLException e) {
            System.err.println("Error fetching clients: " + e.getMessage());
            e.printStackTrace();
        }

        return clientList;
    }

    @Override
    public void updateClient(int id,Client client) {
        String query = "UPDATE client SET name = ?, address = ?, phone = ?, is_professional = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getAddress());
            statement.setString(3, client.getPhone());
            statement.setBoolean(4, client.isIs_professional());
            statement.setInt(5,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClient(int id) {
        String query = "DELETE FROM client WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
