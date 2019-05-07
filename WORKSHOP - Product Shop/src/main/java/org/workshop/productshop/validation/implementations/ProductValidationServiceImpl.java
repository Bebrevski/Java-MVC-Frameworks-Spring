package org.workshop.productshop.validation.implementations;

import org.springframework.stereotype.Component;
import org.workshop.productshop.domain.entities.Product;
import org.workshop.productshop.validation.ProductValidationService;

@Component
public class ProductValidationServiceImpl implements ProductValidationService {
    @Override
    public boolean isValid(Product product) {
        return product == null;
    }
}
