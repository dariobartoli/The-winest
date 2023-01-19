package com.ecommerce.wines.services;

import com.ecommerce.wines.DTOS.PurchaseOrderDTO;
import com.ecommerce.wines.models.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderService {
    public List<PurchaseOrderDTO> getPurchaseOrder();

    public void savePurchaseOrder(PurchaseOrder purchaseOrder);

    PurchaseOrder findById(Long id);
}
