package org.workshop.productshop.validation.implementations;

import org.springframework.stereotype.Component;
import org.workshop.productshop.domain.entities.Product;
import org.workshop.productshop.domain.models.service.CategoryServiceModel;
import org.workshop.productshop.domain.models.service.ProductServiceModel;
import org.workshop.productshop.validation.ProductValidationService;

import java.util.List;

@Component
public class ProductValidationServiceImpl implements ProductValidationService {
    @Override
    public boolean isValid(Product product) {
        return product != null;
    }

    @Override
    public boolean isValid(ProductServiceModel productServiceModel) {
        return productServiceModel != null && areCategoriesValid(productServiceModel.getCategories());
    }

    private boolean areCategoriesValid(List<CategoryServiceModel> categories){
        return categories != null && !categories.isEmpty();
    }
}
