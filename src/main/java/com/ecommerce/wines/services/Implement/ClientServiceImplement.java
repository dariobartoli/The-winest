package com.ecommerce.wines.services.Implement;

import com.ecommerce.wines.DTOS.ClientDTO;
import com.ecommerce.wines.models.Client;
import com.ecommerce.wines.repositories.ClientRepository;
import com.ecommerce.wines.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    ClientRepository clientRepository;


    @Override
    public List<ClientDTO> getClientsDTO() {
        return clientRepository.findAll().stream().map(client->new ClientDTO(client)).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientDTO(Long id) {
        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }

    @Override
    public Client clientFindByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    @Override
    public Client findByToken(String token) {
        return clientRepository.findByToken(token);
    }

    @Override
    public Set<String> getAllTokens() {
        return clientRepository.findAll().stream().map(Client::getToken).collect(Collectors.toSet());
    }
}
