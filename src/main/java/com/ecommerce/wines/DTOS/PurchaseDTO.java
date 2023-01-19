package com.ecommerce.wines.DTOS;

import com.ecommerce.wines.models.PaymentMethod;

import java.util.ArrayList;

public class PurchaseDTO {

    private ArrayList<ProductOrderDTO> productOrderDTOS;

    private PaymentMethod paymentMethod;

    public PurchaseDTO(ArrayList<ProductOrderDTO> productOrderDTOS, PaymentMethod paymentMethod) {
        this.productOrderDTOS = productOrderDTOS;
        this.paymentMethod = paymentMethod;
    }

    public ArrayList<ProductOrderDTO> getProductOrderDTOS() {
        return productOrderDTOS;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
