package repository.implimentation;

import config.dbConnection;
import model.Material;
import repository.interfaces.MaterialRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialRepository implements MaterialRepositoryInterface {
    private final Connection connection;

    public MaterialRepository() throws SQLException {
        this.connection = dbConnection.getInstance().getConnection();
    }

    @Override
    public void addMaterial(Material material) {
        String sql = "INSERT INTO material (name, type_component, vat_rate, project_id, unit_cost, quantity, transport_cost, coefficient_quality) "
                + "VALUES (?, 'Material', ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setMaterialFields(stmt, material);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logError("Error adding material", e);
        }
    }

    @Override
    public void updateMaterial(int id, Material material) {
        String sql = "UPDATE material SET name = ?, vat_rate = ?, project_id = ?, unit_cost = ?, quantity = ?, transport_cost = ?, coefficient_quality = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setMaterialFields(stmt, material);
            stmt.setInt(8, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No Material found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error updating material with id: " + id, e);
        }
    }

    @Override
    public void deleteMaterial(int id) {
        String sql = "DELETE FROM material WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No Material found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error deleting material with id: " + id, e);
        }
    }

    @Override
    public List<Material> getAllMaterials() {
        String sql = "SELECT c.id, c.name, c.vat_rate, c.project_id, m.unit_cost, m.quantity, m.transport_cost, m.coefficient_quality " +
                "FROM material m INNER JOIN component c ON m.id = c.id";
        List<Material> materials = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                materials.add(buildMaterialFromResultSet(rs));
            }
        } catch (SQLException e) {
            logError("Error fetching all materials", e);
        }

        return materials;
    }

    @Override
    public Material getMaterialById(int id) {
        String sql = "SELECT c.id, c.name, c.vat_rate, c.project_id, m.unit_cost, m.quantity, m.transport_cost, m.coefficient_quality " +
                "FROM material m INNER JOIN component c ON m.id = c.id WHERE c.id = ?";
        Material material = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                material = buildMaterialFromResultSet(rs);
            } else {
                System.out.println("No Material found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error fetching material with id: " + id, e);
        }

        return material;
    }

    // Helper method to log errors
    private void logError(String message, SQLException e) {
        System.err.println(message + ": " + e.getMessage());
        // Optional: Use a logging framework like Log4j or SLF4J instead of System.err
    }

    // Helper method to set the fields for a Material in a PreparedStatement
    private void setMaterialFields(PreparedStatement statement, Material material) throws SQLException {
        statement.setString(1, material.getName());
        statement.setDouble(2, material.getVat_rate());
        statement.setInt(3, material.getProject_id());
        statement.setDouble(4, material.getUnit_cost());
        statement.setDouble(5, material.getQuantity());
        statement.setDouble(6, material.getTransport_cost());
        statement.setDouble(7, material.getCoefficient_quality());
    }

    // Helper method to build a Material object from a ResultSet
    private Material buildMaterialFromResultSet(ResultSet resultSet) throws SQLException {
        return new Material(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("vat_rate"),
                resultSet.getDouble("unit_cost"),
                resultSet.getDouble("quantity"),
                resultSet.getDouble("transport_cost"),
                resultSet.getDouble("coefficient_quality"),
                resultSet.getInt("project_id")
        );
    }
}
