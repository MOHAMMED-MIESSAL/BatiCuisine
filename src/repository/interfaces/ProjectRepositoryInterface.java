package repository.interfaces;

import model.Project;
import java.util.List;
import java.util.Map;

public interface ProjectRepositoryInterface {
    Project addProject(Project project);
    Project getProjectById(int id);
    List<Project> getAllProjects();
    void updateProject(int id,Project project);
    void deleteProject(int id);
    Map<String, List<?>> getComponentsByProjectId(int id);
}
