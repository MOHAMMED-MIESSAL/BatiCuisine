package service;

import model.Project;
import repository.interfaces.ProjectRepositoryInterface;

import java.util.List;

public class ProjectService {
    private ProjectRepositoryInterface projectRepository;

    public ProjectService(ProjectRepositoryInterface projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void addProject(Project project) {
        projectRepository.addProject(project);
    }

    public Project getProjectById(int id) {
        return projectRepository.getProjectById(id);
    }

    public List<Project> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    public void updateProject(int id,Project project) {
        projectRepository.updateProject(id,project);
    }

    public void deleteProject(int id) {
        projectRepository.deleteProject(id);
    }
}
