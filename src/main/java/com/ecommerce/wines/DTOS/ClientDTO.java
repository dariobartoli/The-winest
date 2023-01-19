package com.ecommerce.wines.DTOS;

import com.ecommerce.wines.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private  String password;

    private String image;

    private String token;

    private boolean active;

    private Set<PurchaseOrderDTO> purchaseOrders;

    private Set<MomentDTO> moments;

    private Set<FavsDTO> favsDTO;

    public ClientDTO(Client client) {
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.password = client.getPassword();
        this.image = client.getImage();
        this.active = client.isActive();
        this.purchaseOrders = client.getPurchaseOrders().stream().map(PurchaseOrderDTO::new).collect(Collectors.toSet());
        this.moments = client.getMoments().stream().map(MomentDTO::new).collect(Collectors.toSet());
        this.token = client.getToken();
        this.favsDTO = client.getFavss().stream().map(FavsDTO::new).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImage() {
        return image;
    }

    public String getToken() {
        return token;
    }

    public boolean isActive() {
        return active;
    }

    public Set<PurchaseOrderDTO> getPurchaseOrders() {
        return purchaseOrders;
    }

    public Set<MomentDTO> getMoments() {
        return moments;
    }

    public Set<FavsDTO> getFavsDTO() {
        return favsDTO;
    }
}
