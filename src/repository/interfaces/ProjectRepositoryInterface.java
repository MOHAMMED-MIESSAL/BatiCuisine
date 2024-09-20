package repository.interfaces;

import model.Project;

import java.util.List;

public interface ProjectRepositoryInterface {
    void addProject(Project project);
    Project getProjectById(int id);
    List<Project> getAllProjects();
    void updateProject(int id,Project project);
    void deleteProject(int id);
}
