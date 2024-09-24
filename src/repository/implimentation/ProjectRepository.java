package repository.implimentation;

import config.dbConnection;
import enums.ProjectStatus;
import model.Labor;
import model.Material;
import model.Project;
import repository.interfaces.ProjectRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectRepository implements ProjectRepositoryInterface {
    private final Connection connection;

    public ProjectRepository() throws SQLException {
        this.connection = dbConnection.getInstance().getConnection();
    }

    @Override
    public Project addProject(Project project) {
        String query = "INSERT INTO project (name, profit_margin, state_project, total_cost, client_id) VALUES (?, ?, ?::project_status, ?, ?) RETURNING id";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, project.getName());
            statement.setDouble(2, project.getProfit_margin() != null ? project.getProfit_margin() : 0.0);
            statement.setString(3, project.getState_project() != null ? project.getState_project().name() : "PENDING");
            statement.setDouble(4, project.getTotal_cost() != null ? project.getTotal_cost() : 0.0);
            statement.setInt(5, project.getClient_id());

            // Exécuter la requête et récupérer l'ID généré
            ResultSet generatedKeys = statement.executeQuery();
            if (generatedKeys.next()) {
                project.setId(generatedKeys.getInt(1)); // Mettre à jour l'ID du projet
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project; // Retourner le projet avec l'ID mis à jour
    }


    @Override
    public Project getProjectById(int id) {
        String query = "SELECT * FROM project WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return buildProjectFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logError("Error fetching project with id: " + id, e);
        }
        return null;
    }

    @Override
    public void updateProject(int id, Project project) {
        String query = "UPDATE project SET name = ?, profit_margin = ?, state_project = ?::project_status, total_cost = ?, client_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setProjectFields(statement, project);
            statement.setInt(6, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No Project found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error updating project with id: " + id, e);
        }
    }

    @Override
    public void deleteProject(int id) {
        String query = "DELETE FROM project WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No Project found with id: " + id);
            }
        } catch (SQLException e) {
            logError("Error deleting project with id: " + id, e);
        }
    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM project";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                projects.add(buildProjectFromResultSet(resultSet));
            }

            if (projects.isEmpty()) {
                System.out.println("No data found");
            }
        } catch (SQLException e) {
            logError("Error fetching all projects", e);
        }
        return projects;
    }

    @Override
    // Get components by project id
    public Map<String, List<?>> getComponentsByProjectId(int projectId) {
        Map<String, List<?>> componentsMap = new HashMap<>();

        // Récupérer les matériaux
        List<Material> materials = new ArrayList<>();
        String materialQuery = "SELECT * FROM material WHERE project_id = ?";
        try (PreparedStatement materialStatement = connection.prepareStatement(materialQuery)) {
            materialStatement.setInt(1, projectId);
            ResultSet materialResultSet = materialStatement.executeQuery();
            while (materialResultSet.next()) {
                // Créer l'objet Material à partir des résultats
                Material material = new Material(
                        materialResultSet.getInt("id"),
                        materialResultSet.getString("name"),
                        materialResultSet.getDouble("vat_rate"),
                        materialResultSet.getDouble("unit_cost"),
                        materialResultSet.getDouble("quantity"),
                        materialResultSet.getDouble("transport_cost"),
                        materialResultSet.getDouble("coefficient_quality"),
                        projectId
                );
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        componentsMap.put("material", materials);

        // Récupérer les travaux (labors)
        List<Labor> labors = new ArrayList<>();
        String laborQuery = "SELECT * FROM labor WHERE project_id = ?";
        try (PreparedStatement laborStatement = connection.prepareStatement(laborQuery)) {
            laborStatement.setInt(1, projectId);
            ResultSet laborResultSet = laborStatement.executeQuery();
            while (laborResultSet.next()) {
                // Créer l'objet Labor à partir des résultats
                Labor labor = new Labor(
                        laborResultSet.getInt("id"),
                        laborResultSet.getString("name"),
                        laborResultSet.getDouble("vat_rate"),
                        projectId,
                        laborResultSet.getDouble("hourly_rate"),
                        laborResultSet.getDouble("hours_work"),
                        laborResultSet.getDouble("worker_productivity")
                );
                labors.add(labor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        componentsMap.put("labor", labors);

        return componentsMap;
    }


    // Helper method to log errors
    private void logError(String message, SQLException e) {
        System.err.println(message + ": " + e.getMessage());
        // Optional: Use a logging framework like Log4j or SLF4J instead of System.err
    }

    // Helper method to set the fields for a Project in a PreparedStatement
    private void setProjectFields(PreparedStatement statement, Project project) throws SQLException {
        statement.setString(1, project.getName());
        statement.setDouble(2, project.getProfit_margin());
        statement.setString(3, project.getState_project().name());
        statement.setDouble(4, project.getTotal_cost());
        statement.setInt(5, project.getClient_id());
    }

    // Helper method to build a Project object from a ResultSet
    private Project buildProjectFromResultSet(ResultSet resultSet) throws SQLException {
        return new Project(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("profit_margin"),
                ProjectStatus.valueOf(resultSet.getString("state_project")),
                resultSet.getDouble("total_cost"),
                resultSet.getInt("client_id")
        );
    }
}
