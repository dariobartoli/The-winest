package com.ecommerce.wines.services;

import com.ecommerce.wines.DTOS.ClientDTO;
import com.ecommerce.wines.models.Client;

import java.util.List;
import java.util.Set;

public interface ClientService {
    public List<ClientDTO> getClientsDTO();

    public ClientDTO getClientDTO(Long id);

    public Client clientFindByEmail(String email);

    public void saveClient(Client client);

    public void deleteClient(Client client);

    Client findByToken(String token);

    Set<String> getAllTokens ();
}
