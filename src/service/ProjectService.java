package service;

import model.Labor;
import model.Material;
import model.Project;
import repository.interfaces.ProjectRepositoryInterface;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProjectService {
    private final ProjectRepositoryInterface projectRepository;

    public ProjectService(ProjectRepositoryInterface projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project addProject(Project project) {
       return projectRepository.addProject(project);
    }

    public Project getProjectById(int id) {
        return projectRepository.getProjectById(id);
    }

    public List<Project> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    public void updateProject(int id, Project project) {
        projectRepository.updateProject(id, project);
    }

    public void deleteProject(int id) {
        projectRepository.deleteProject(id);
    }

    public Map<String, List<?>> getComponentsByProjectId(int id) {
        return projectRepository.getComponentsByProjectId(id);
    }

}
