package org.workshop.productshop.validation;

import org.workshop.productshop.domain.entities.Product;
import org.workshop.productshop.domain.entities.User;
import org.workshop.productshop.domain.models.service.ProductServiceModel;

public interface ProductValidationService {
    boolean isValid(Product product);

    boolean isValid(ProductServiceModel productServiceModel);
}
