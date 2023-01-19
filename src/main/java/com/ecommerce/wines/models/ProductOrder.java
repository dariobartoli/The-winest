package com.ecommerce.wines.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private Integer quantity;

    private Double Amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="purchaseOrder_id")
    private PurchaseOrder purchaseOrder;

    public ProductOrder() {
    }

    public ProductOrder(Integer quantity, Product product, PurchaseOrder purchaseOrder) {
        this.quantity = quantity;
        this.Amount = product.getPrice() * quantity;
        this.product = product;
        this.purchaseOrder = purchaseOrder;
    }

    public long getId() {
        return id;
    }



    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
