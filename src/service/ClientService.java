package service;

import model.Client;
import repository.interfaces.ClientRepositoryInterface;

import java.util.List;

public class ClientService {
    private final ClientRepositoryInterface clientRepository;

    public ClientService(ClientRepositoryInterface clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void addClient(Client client) {
        clientRepository.addClient(client);
    }

    public Client getClientById(int id) {
        return clientRepository.getClientById(id);
    }

    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    public void updateClient(int id, Client client) {
        clientRepository.updateClient(id, client);
    }

    public void deleteClient(int id) {
        clientRepository.deleteClient(id);
    }

    public Client getClientByName(String name) {
     return  clientRepository.getClientByName(name);
    }
}
