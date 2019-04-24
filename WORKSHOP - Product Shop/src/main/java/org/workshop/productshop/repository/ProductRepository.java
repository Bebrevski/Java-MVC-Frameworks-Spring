package org.workshop.productshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workshop.productshop.domain.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
