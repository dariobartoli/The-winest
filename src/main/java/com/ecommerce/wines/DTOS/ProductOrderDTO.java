package com.ecommerce.wines.DTOS;

import com.ecommerce.wines.models.Client;
import com.ecommerce.wines.models.ProductOrder;
import com.ecommerce.wines.models.PurchaseOrder;

public class ProductOrderDTO {

    private long id;
    private String productName;
    private Integer quantity;
    private Double amount;


    public ProductOrderDTO(String productName, Integer quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public ProductOrderDTO(ProductOrder productOrder) {
        this.id = productOrder.getId();
        this.productName = productOrder.getProduct().getName();
        this.quantity = productOrder.getQuantity();
        this.amount = productOrder.getAmount();
    }

    public long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getAmount() {
        return amount;
    }
}
