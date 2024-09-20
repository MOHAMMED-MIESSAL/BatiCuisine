package service;

import model.Client;
import repository.interfaces.ClientRepositoryInterface;

import java.util.List;

public class ClientService {
    private ClientRepositoryInterface userRepository;

    public ClientService(ClientRepositoryInterface userRepository) {

        this.userRepository = userRepository;
    }

    public void addClient(Client client) {
        userRepository.addClient(client);
    }

    public Client getClientById(int id) {
        return userRepository.getClientById(id);
    }

    public List<Client> getAllClients() {
        return userRepository.getAllClients();
    }

    public void updateClient(int id,Client client) {
        userRepository.updateClient(id,client);
    }

    public void deleteClient(int id) {
        userRepository.deleteClient(id);
    }
}
