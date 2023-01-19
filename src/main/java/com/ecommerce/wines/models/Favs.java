package com.ecommerce.wines.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Favs {

    @Id
    @GeneratedValue
    @GenericGenerator(name="native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    private String name;
    private String image;



    public Favs() {
    }

    public Favs(Product product, Client client, String name, String image) {
        this.product = product;
        this.client = client;
        this.name = name;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
