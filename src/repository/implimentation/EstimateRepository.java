package repository.implimentation;

import config.dbConnection;
import model.Estimate;
import repository.interfaces.EstimateRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstimateRepository implements EstimateRepositoryInterface {
    private final Connection connection;

    public EstimateRepository() throws SQLException {
        this.connection = dbConnection.getInstance().getConnection();
    }

    @Override
    public void addEstimate(Estimate estimate) {
        String sql = "INSERT INTO estimate (amount_estimate, date_emission, date_validity, is_accepted, project_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setEstimateFields(statement, estimate);
            statement.executeUpdate();
        } catch (SQLException e) {
            logError("Error adding estimate", e);
        }
    }

    @Override
    public void deleteEstimate(int id) {
        String sql = "DELETE FROM estimate WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No Estimate found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error deleting estimate with id: " + id, e);
        }
    }

    @Override
    public List<Estimate> getAllEstimates() {
        String sql = "SELECT * FROM estimate";
        List<Estimate> estimates = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                estimates.add(buildEstimateFromResultSet(resultSet));
            }

            if (estimates.isEmpty()) {
                System.out.println("No Estimates found");
            }

        } catch (SQLException e) {
            logError("Error fetching all estimates", e);
        }

        return estimates;
    }

    @Override
    public Estimate getEstimateById(int id) {
        String sql = "SELECT * FROM estimate WHERE id = ?";
        Estimate estimate = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                estimate = buildEstimateFromResultSet(resultSet);
            } else {
                System.out.println("No Estimate found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error fetching estimate with id: " + id, e);
        }

        return estimate;
    }

    @Override
    public void updateEstimate(int id, Estimate estimate) {
        String sql = "UPDATE estimate SET amount_estimate = ?, date_emission = ?, date_validity = ?, is_accepted = ?, project_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setEstimateFields(statement, estimate);
            statement.setInt(6, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No Estimate found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error updating estimate with id: " + id, e);
        }
    }

    // Helper method to log errors
    private void logError(String message, SQLException e) {
        System.err.println(message + ": " + e.getMessage());
    }

    private void setEstimateFields(PreparedStatement statement, Estimate estimate) throws SQLException {
        statement.setDouble(1, estimate.getAmount_estimate());
        statement.setDate(2, estimate.getDate_emission());
        statement.setDate(3, estimate.getDate_validity());

        if (estimate.getIs_accepted() != null) {
            statement.setBoolean(4, estimate.getIs_accepted());
        } else {
            statement.setNull(4, Types.BOOLEAN);
        }

        statement.setInt(5, estimate.getProject_id());
    }

    // Helper method to build an Estimate object from a ResultSet
    private Estimate buildEstimateFromResultSet(ResultSet resultSet) throws SQLException {
        return new Estimate(
                resultSet.getInt("id"),
                resultSet.getDouble("amount_estimate"),
                resultSet.getDate("date_emission"),
                resultSet.getDate("date_validity"),
                resultSet.getBoolean("is_accepted"),
                resultSet.getInt("project_id")
        );
    }
}
