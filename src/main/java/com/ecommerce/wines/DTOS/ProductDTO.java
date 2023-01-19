package com.ecommerce.wines.DTOS;

import com.ecommerce.wines.models.Category;
import com.ecommerce.wines.models.Product;
import com.ecommerce.wines.models.PurchaseOrder;


public class ProductDTO {

    private long id;

    private Category category;

    private String name;

    private String description;

    private int stock;

    private double price;

    private double discount;

    private String image;

    private String variety;

    private String tastingNote;

    private String temperature;


    private boolean active;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.category = product.getCategory();
        this.name = product.getName();
        this.description = product.getDescription();
        this.stock = product.getStock();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.image = product.getImage();
        this.variety = product.getVariety();
        this.tastingNote = product.getTastingNote();
        this.temperature = product.getTemperature();
        this.active = product.isActive();
    }

    public long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public String getImage() {
        return image;
    }

    public String getVariety() {
        return variety;
    }

    public String getTastingNote() {
        return tastingNote;
    }

    public String getTemperature() {
        return temperature;
    }


    public boolean isActive() {
        return active;
    }

}
