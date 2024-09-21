
import enums.ProjectStatus;
import model.*;
import repository.implimentation.*;
import repository.interfaces.*;
import service.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {

        ClientRepositoryInterface clientRepository = new ClientRepository();
        ClientService clientService = new ClientService(clientRepository);
        ProjectRepositoryInterface projectRepository = new ProjectRepository();
        ProjectService projectService = new ProjectService(projectRepository);
        MaterialRepositoryInterface materialRepository = new MaterialRepository();
        MaterialService materialService = new MaterialService(materialRepository);
        LabelRepositoryInterface labelRepository = new LabelRepository();
        LabelService labelService = new LabelService(labelRepository);
        EstimateRepositoryInterface estimateRepository = new EstimateRepository();
        EstimateService estimateService = new EstimateService(estimateRepository);

//        // ADD Client
//        clientService.addClient(new Client(0, "Med", "Casablanca", "065-354-2784", true));
    //  clientService.updateClient(1,new Client(0, "SIKO", "123 Main St", "123-456-7890", true));

        // GET ALL Clients
//        List<Client> clients = clientService.getAllClients();
//        for (Client client : clients) {
//            System.out.println(client);
//        }
//        clientService.deleteClient(1);



//
//         //ADD PROJECT
//
//        Project project = new Project(0, "Project Test 1", 567000, ProjectStatus.InPROGRESS, 40000, 1);
//        projectService.addProject(project);
//
//        projectService.getAllProjects().forEach(System.out::println);


//
//         //ADD MATERIAL
//        Material addmaterial = new Material(0, "POTO", 1, 15, 1, 272, 293, 400);
//        materialService.addMaterial(addmaterial);

        //UPDATE MATERIAL
//        Material updatematerial = new Material(0, "SADIO MANE", 1, 5, 80, 400, 100, 1.1);
//        materialService.updateMaterial(1, updatematerial);

        // GET ALL MATERIALS
//        List<Material> materials = materialService.getAllMaterials();
//        for (Material material : materials) {
//            System.out.println(material);
//        }
        // ADD LABEL
//        Label label = new Label(0, "Label 1", 1, 10.5, 10, 100, 2);
//        labelService.addLabel(label);

        // GET ALL LABELS
//        List<Label> labels = labelService.getAllLabels();
//        for (Label label : labels) {
//            System.out.println(label);
//        }
        // Update Label
//        Label updatelabel = new Label(0, "Label XX", 1, 9, 3, 32, 2);
//        labelService.updateLabel(1, updatelabel);

        // DELETE LABEL
//        labelService.deleteLabel(1);

        // Add Estimate
//        Estimate estimate = new Estimate(0, 100, Date.valueOf("2021-10-10"), Date.valueOf("2021-10-20"), true, 1);
//        estimateService.addEstimate(estimate);

        // GET ALL ESTIMATES
//        List<Estimate> estimates = estimateService.getAllEstimates();
//        for(Estimate estimate : estimates) {
//            System.out.println(estimate);
//        }

        // UPDATE ESTIMATE
//       Estimate updateestimate = new Estimate(0, 200, Date.valueOf("2021-10-03"), Date.valueOf("2021-10-25"), false, 1);
//         estimateService.updateEstimate(1, updateestimate);

        // DELETE ESTIMATE
//        estimateService.deleteEstimate(1);
    }
}