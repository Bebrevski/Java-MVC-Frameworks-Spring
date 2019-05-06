package org.workshop.productshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workshop.productshop.domain.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
