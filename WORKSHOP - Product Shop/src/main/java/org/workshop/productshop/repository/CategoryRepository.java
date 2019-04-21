package org.workshop.productshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workshop.productshop.domain.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
}
