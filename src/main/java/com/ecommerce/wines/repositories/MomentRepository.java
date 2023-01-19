package com.ecommerce.wines.repositories;


import com.ecommerce.wines.models.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MomentRepository extends JpaRepository<Moment, Long> {
}
