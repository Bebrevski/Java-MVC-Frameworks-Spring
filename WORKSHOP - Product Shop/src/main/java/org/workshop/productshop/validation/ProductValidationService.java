package org.workshop.productshop.validation;

import org.workshop.productshop.domain.entities.Product;
import org.workshop.productshop.domain.entities.User;

public interface ProductValidationService {
    boolean isValid(Product product);
}
