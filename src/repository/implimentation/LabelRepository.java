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
    private Connection connection;

    public LabelRepository() {
        try {
            this.connection = dbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addLabel(Label label) {
        String queryMaterial = "INSERT INTO label (id, name, type_component, vat_rate, project_id, hourly_rate, hours_work, worker_productivity) "
                + "VALUES (DEFAULT, ?, 'Label', ?, ?, ?, ?, ?)";
        try (PreparedStatement stmtLabel = connection.prepareStatement(queryMaterial, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmtLabel.setString(1, label.getName());
            stmtLabel.setDouble(2, label.getVat_rate());
            stmtLabel.setInt(3, label.getProject_id());
            stmtLabel.setDouble(4, label.getHourly_rate());
            stmtLabel.setDouble(5, label.getHours_work());
            stmtLabel.setDouble(6, label.getWorker_productivity());
            stmtLabel.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLabel(int id) {
        String query = "DELETE FROM label WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Label> getAllLabels() {
        String sql = "SELECT * FROM label";
        List<Label> labels = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Label label = new Label(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("project_id"),
                        rs.getDouble("vat_rate"),
                        rs.getDouble("hourly_rate"),
                        rs.getDouble("hours_work"),
                        rs.getDouble("worker_productivity")
                );
                labels.add(label);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return labels;
    }

    @Override
    public Label getLabelById(int id) {
        String query = "SELECT * FROM label WHERE id = ?";
        Label label = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                 label = new Label(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("project_id"),
                        resultSet.getDouble("vat_rate"),
                        resultSet.getDouble("hourly_rate"),
                        resultSet.getDouble("hours_work"),
                        resultSet.getDouble("worker_productivity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    @Override
    public void updateLabel(int id,Label label) {
        String query = "UPDATE label SET name = ?, vat_rate = ?, project_id = ?, hourly_rate = ?, hours_work = ?, worker_productivity = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, label.getName());
            statement.setDouble(2, label.getVat_rate());
            statement.setInt(3, label.getProject_id());
            statement.setDouble(4, label.getHourly_rate());
            statement.setDouble(5, label.getHours_work());
            statement.setDouble(6, label.getWorker_productivity());
            statement.setInt(7, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
