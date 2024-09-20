
import enums.ProjectStatus;
import model.Project;
import repository.implimentation.ClientRepository;
import repository.implimentation.ProjectRepository;
import repository.interfaces.ClientRepositoryInterface;
import repository.interfaces.ProjectRepositoryInterface;
import service.ClientService;
import service.ProjectService;



public class Main {
    public static void main(String[] args) {

        ClientRepositoryInterface clientRepository = new ClientRepository();
        ClientService clientService = new ClientService(clientRepository);
        ProjectRepositoryInterface projectRepository = new ProjectRepository();
        ProjectService projectService = new ProjectService(projectRepository);


        // ADD USER
//         clientService.addClient(new Client(0,"MOMO", "123 Main St", "123-456-7890", true));
//        clientService.updateClient(1,new Client(0,"MOMO", "Casablanca", "065-354-2784", false));


        // ADD PROJECT
        Project project = new Project(0, "Project 2", 567000, ProjectStatus.COMPLETED, 40000, 1);
        projectService.addProject(project);
        //Update Project
//        projectService.updateProject(2,new Project(0, "Updated Project", 4000, ProjectStatus.InPROGRESS, 8574, 1));





    }
}