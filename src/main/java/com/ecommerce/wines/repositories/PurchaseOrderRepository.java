package com.ecommerce.wines.repositories;

import com.ecommerce.wines.models.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
