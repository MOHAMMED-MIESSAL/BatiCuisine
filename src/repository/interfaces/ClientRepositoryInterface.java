package repository.interfaces;

import model.Client;
import java.util.List;

public interface ClientRepositoryInterface {
    void addClient(Client client);
    Client getClientById(int id);
    List<Client> getAllClients();
    void updateClient(int id ,Client client);
    void deleteClient(int id);

}
