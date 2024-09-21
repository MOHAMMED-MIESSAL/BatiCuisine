package repository.implimentation;

import config.dbConnection;
import model.Label;
import repository.interfaces.LabelRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LabelRepository implements LabelRepositoryInterface {
    private final Connection connection;

    public LabelRepository() throws SQLException {
        this.connection = dbConnection.getInstance().getConnection();
    }

    @Override
    public void addLabel(Label label) {
        String sql = "INSERT INTO label (name, type_component, vat_rate, project_id, hourly_rate, hours_work, worker_productivity) "
                + "VALUES (?, 'Label', ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setLabelFields(stmt, label);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logError("Error adding label", e);
        }
    }

    @Override
    public void deleteLabel(int id) {
        String sql = "DELETE FROM label WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No Label found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error deleting label with id: " + id, e);
        }
    }

    @Override
    public List<Label> getAllLabels() {
        String sql = "SELECT * FROM label";
        List<Label> labels = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                labels.add(buildLabelFromResultSet(rs));
            }

            if (labels.isEmpty()) {
                System.out.println("No Labels found");
            }

        } catch (SQLException e) {
            logError("Error fetching all labels", e);
        }

        return labels;
    }

    @Override
    public Label getLabelById(int id) {
        String sql = "SELECT * FROM label WHERE id = ?";
        Label label = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                label = buildLabelFromResultSet(resultSet);
            } else {
                System.out.println("No Label found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error fetching label with id: " + id, e);
        }

        return label;
    }

    @Override
    public void updateLabel(int id, Label label) {
        String sql = "UPDATE label SET name = ?, vat_rate = ?, project_id = ?, hourly_rate = ?, hours_work = ?, worker_productivity = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setLabelFields(statement, label);
            statement.setInt(7, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No Label found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error updating label with id: " + id, e);
        }
    }

    // Helper method to log errors
    private void logError(String message, SQLException e) {
        System.err.println(message + ": " + e.getMessage());
        // Optional: Use a logging framework like Log4j or SLF4J instead of System.err
    }

    // Helper method to set the fields for a Label in a PreparedStatement
    private void setLabelFields(PreparedStatement statement, Label label) throws SQLException {
        statement.setString(1, label.getName());
        statement.setDouble(2, label.getVat_rate());
        statement.setInt(3, label.getProject_id());
        statement.setDouble(4, label.getHourly_rate());
        statement.setDouble(5, label.getHours_work());
        statement.setDouble(6, label.getWorker_productivity());
    }

    // Helper method to build a Label object from a ResultSet
    private Label buildLabelFromResultSet(ResultSet resultSet) throws SQLException {
        return new Label(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("project_id"),
                resultSet.getDouble("vat_rate"),
                resultSet.getDouble("hourly_rate"),
                resultSet.getDouble("hours_work"),
                resultSet.getDouble("worker_productivity")
        );
    }
}
