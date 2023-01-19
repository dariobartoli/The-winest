package com.ecommerce.wines.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue
    @GenericGenerator(name="native", strategy = "native")
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private  String password;

    @OneToMany(mappedBy="client", fetch= FetchType.EAGER)
    private Set<PurchaseOrder> purchaseOrders = new HashSet<>();

    @OneToMany(mappedBy="client", fetch= FetchType.EAGER)
    private Set<Moment> moments = new HashSet<>();

    @OneToMany(mappedBy="client", fetch= FetchType.EAGER)
    private Set<Favs> favss = new HashSet<>();

    private String image;

    private String token;

    private boolean active;



    public Client() {
    }

    public Client(String firstName, String lastName, String email, String password,String image , String token, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.token = token;
        this.active = active;
        this.image=image;
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

    public Set<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public Set<Moment> getMoments() {
        return moments;
    }

    public String getToken() {
        return token;
    }

    public boolean isActive() {
        return active;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Favs> getFavss() {
        return favss;
    }

    public void setFavss(Set<Favs> favss) {
        this.favss = favss;
    }

    public void addMoment(Moment moment) {
        moment.setClient(this);
        moments.add(moment);
    }

    public void addFavs(Favs favs) {
        favs.setClient(this);
        favss.add(favs);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}


