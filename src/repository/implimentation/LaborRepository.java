package repository.implimentation;

import config.dbConnection;
import model.Labor;
import repository.interfaces.LaborRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaborRepository implements LaborRepositoryInterface {
    private final Connection connection;

    public LaborRepository() throws SQLException {
        this.connection = dbConnection.getInstance().getConnection();
    }

    @Override
    public void addLabor(Labor Labor) {
        String sql = "INSERT INTO Labor (name, type_component, vat_rate, project_id, hourly_rate, hours_work, worker_productivity) "
                + "VALUES (?, 'Labor', ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setLaborFields(stmt, Labor);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logError("Error adding Labor", e);
        }
    }

    @Override
    public void deleteLabor(int id) {
        String sql = "DELETE FROM Labor WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No Labor found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error deleting Labor with id: " + id, e);
        }
    }

    @Override
    public List<Labor> getAllLabors() {
        String sql = "SELECT * FROM Labor";
        List<Labor> Labors = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Labors.add(buildLaborFromResultSet(rs));
            }

            if (Labors.isEmpty()) {
                System.out.println("No Labors found");
            }

        } catch (SQLException e) {
            logError("Error fetching all Labors", e);
        }

        return Labors;
    }

    @Override
    public Labor getLaborById(int id) {
        String sql = "SELECT * FROM Labor WHERE id = ?";
        Labor Labor = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Labor = buildLaborFromResultSet(resultSet);
            } else {
                System.out.println("No Labor found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error fetching Labor with id: " + id, e);
        }

        return Labor;
    }

    @Override
    public void updateLabor(int id, Labor Labor) {
        String sql = "UPDATE Labor SET name = ?, vat_rate = ?, project_id = ?, hourly_rate = ?, hours_work = ?, worker_productivity = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setLaborFields(statement, Labor);
            statement.setInt(7, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No Labor found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error updating Labor with id: " + id, e);
        }
    }

    // Helper method to log errors
    private void logError(String message, SQLException e) {
        System.err.println(message + ": " + e.getMessage());
    }

    // Helper method to set the fields for a Labor in a PreparedStatement
    private void setLaborFields(PreparedStatement statement, Labor Labor) throws SQLException {
        statement.setString(1, Labor.getName());
        statement.setDouble(2, Labor.getVat_rate());
        statement.setInt(3, Labor.getProject_id());
        statement.setDouble(4, Labor.getHourly_rate());
        statement.setDouble(5, Labor.getHours_work());
        statement.setDouble(6, Labor.getWorker_productivity());
    }

    // Helper method to build a Labor object from a ResultSet
    private Labor buildLaborFromResultSet(ResultSet resultSet) throws SQLException {
        return new Labor(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("vat_rate"),
                resultSet.getInt("project_id"),
                resultSet.getDouble("hourly_rate"),
                resultSet.getDouble("hours_work"),
                resultSet.getDouble("worker_productivity")
        );
    }
}
