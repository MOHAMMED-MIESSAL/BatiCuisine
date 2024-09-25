package service;

import model.Client;
import repository.interfaces.ClientRepositoryInterface;

import java.util.List;

public class ClientService implements ClientServiceInterface {
    private final ClientRepositoryInterface clientRepository;

    public ClientService(ClientRepositoryInterface clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void addClient(Client client) {
        clientRepository.addClient(client);
    }

    @Override
    public Client getClientById(int id) {
        return clientRepository.getClientById(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    @Override
    public void updateClient(int id, Client client) {
        clientRepository.updateClient(id, client);
    }

    @Override
    public void deleteClient(int id) {
        clientRepository.deleteClient(id);
    }

    @Override
    public Client getClientByName(String name) {
     return  clientRepository.getClientByName(name);
    }
}
