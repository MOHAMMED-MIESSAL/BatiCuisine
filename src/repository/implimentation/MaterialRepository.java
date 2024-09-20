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
    private Connection connection;

    public MaterialRepository() {
        try {
            this.connection = dbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addMaterial(Material material) {
        String queryMaterial = "INSERT INTO material (id, name, type_component, vat_rate, project_id, unit_cost, quantity, transport_cost, coefficient_quality) "
                + "VALUES (DEFAULT, ?, 'Material', ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmtMaterial = connection.prepareStatement(queryMaterial, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmtMaterial.setString(1, material.getName());
            stmtMaterial.setDouble(2, material.getVat_rate());
            stmtMaterial.setInt(3, material.getProject_id());
            stmtMaterial.setDouble(4, material.getUnit_cost());
            stmtMaterial.setDouble(5, material.getQuantity());
            stmtMaterial.setDouble(6, material.getTransport_cost());
            stmtMaterial.setDouble(7, material.getCoefficient_quality());
            stmtMaterial.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateMaterial(int id, Material material) {
        String queryMaterial = "UPDATE material SET name = ?, vat_rate = ?, project_id = ?, unit_cost = ?, quantity = ?, transport_cost = ?, coefficient_quality = ? WHERE id = ?";

        try (PreparedStatement stmtMaterial = connection.prepareStatement(queryMaterial)) {

            stmtMaterial.setString(1, material.getName());
            stmtMaterial.setDouble(2, material.getVat_rate());
            stmtMaterial.setInt(3, material.getProject_id());
            stmtMaterial.setDouble(4, material.getUnit_cost());
            stmtMaterial.setDouble(5, material.getQuantity());
            stmtMaterial.setDouble(6, material.getTransport_cost());
            stmtMaterial.setDouble(7, material.getCoefficient_quality());
            stmtMaterial.setInt(8, id);

            stmtMaterial.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMaterial(int id) {
        String query = "DELETE FROM material WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Material> getAllMaterials() {
        String query = "SELECT c.id, c.name, c.vat_rate, c.project_id, m.unit_cost, m.quantity, m.transport_cost, m.coefficient_quality " +
                "FROM material m INNER JOIN component c ON m.id = c.id";
        List<Material> materials = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Material material = new Material(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("project_id"),
                        rs.getDouble("vat_rate"),
                        rs.getDouble("unit_cost"),
                        rs.getDouble("quantity"),
                        rs.getDouble("transport_cost"),
                        rs.getDouble("coefficient_quality")
                );
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materials;
    }


    @Override
    public Material getMaterialById(int id) {
        String query = "SELECT c.id, c.name, c.vat_rate, c.project_id, m.unit_cost, m.quantity, m.transport_cost, m.coefficient_quality " +
                "FROM material m INNER JOIN component c ON m.id = c.id WHERE c.id = ?";
        Material material = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                material = new Material(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("project_id"),
                        rs.getDouble("vat_rate"),
                        rs.getDouble("unit_cost"),
                        rs.getDouble("quantity"),
                        rs.getDouble("transport_cost"),
                        rs.getDouble("coefficient_quality")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return material;
    }


}
