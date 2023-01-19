package com.ecommerce.wines.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private Category category;

    private String name;

    private String description;

    private int stock;

    private Double price;

    private double discount;

    private String image;

    private String variety;

    private String tastingNote;

    private String temperature;

    private boolean active;

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
    private Set<ProductOrder> productOrder = new HashSet<>();

    public Product() {
    }

    public Product(Category category, String name, String description, int stock, Double price, double discount, String image, String variety, String tastingNote, String temperature, boolean active) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.discount = discount;
        this.image = image;
        this.variety = variety;
        this.tastingNote = tastingNote;
        this.temperature = temperature;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getTastingNote() {
        return tastingNote;
    }

    public void setTastingNote(String tastingNote) {
        this.tastingNote = tastingNote;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<ProductOrder> getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(Set<ProductOrder> productOrder) {
        this.productOrder = productOrder;
    }
}
