package com.ecommerce.wines.services.Implement;

import com.ecommerce.wines.DTOS.ClientDTO;
import com.ecommerce.wines.DTOS.PurchaseOrderDTO;
import com.ecommerce.wines.models.PurchaseOrder;
import com.ecommerce.wines.repositories.PurchaseOrderRepository;
import com.ecommerce.wines.services.ProductService;
import com.ecommerce.wines.services.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderServiceImplement implements PurchaseOrderService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;


    @Override
    public List<PurchaseOrderDTO> getPurchaseOrder() {
        return purchaseOrderRepository.findAll().stream().map(purchaseOrder->new PurchaseOrderDTO(purchaseOrder)).collect(Collectors.toList());
    }

    @Override
    public void savePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderRepository.save(purchaseOrder);

    }

    @Override
    public PurchaseOrder findById(Long id) {
        return purchaseOrderRepository.findById(id).orElse(null);
    }

}
