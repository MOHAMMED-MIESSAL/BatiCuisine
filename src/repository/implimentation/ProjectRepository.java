package repository.implimentation;

import config.dbConnection;
import enums.ProjectStatus;
import model.Project;
import repository.interfaces.ProjectRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository implements ProjectRepositoryInterface {
    private Connection connection;

    public ProjectRepository() {
        try {
            this.connection = dbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addProject(Project project) {
        String query = "INSERT INTO project (name, profit_margin, state_project, total_cost, client_id) VALUES (?, ?, ?::state_project, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, project.getName());
            statement.setDouble(2, project.getProfit_margin());
            statement.setString(3, project.getState_project().name());
            statement.setDouble(4, project.getTotal_cost());
            statement.setInt(5, project.getClient_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Project getProjectById(int id) {
        String query = "SELECT * FROM project WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Project(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("profit_margin"),
                        ProjectStatus.valueOf(resultSet.getString("state_project")), // Conversion String to Enum
                        resultSet.getDouble("total_cost"),
                        resultSet.getInt("client_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateProject(Project project) {
        String query = "UPDATE project SET name = ?, profit_margin = ?, state_project = ?::project_status, total_cost = ?, client_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, project.getName());
            statement.setDouble(2, project.getProfit_margin());
            statement.setString(3, project.getState_project().name()); // Conversion Enum to String
            statement.setDouble(4, project.getTotal_cost());
            statement.setInt(5, project.getClient_id());
            statement.setInt(6, project.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProject(int id) {
        String query = "DELETE FROM project WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM project";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                projects.add(new Project(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("profit_margin"),
                        ProjectStatus.valueOf(resultSet.getString("state_project")), // Conversion String to Enum
                        resultSet.getDouble("total_cost"),
                        resultSet.getInt("client_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }


}
