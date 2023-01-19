package com.ecommerce.wines.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Moment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String image;

    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Moment() {
    }

    public Moment(String image, String title, String description, Client client) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.client = client;
    }

    public Moment(String image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Moment{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", client=" + client +
                '}';
    }
}
