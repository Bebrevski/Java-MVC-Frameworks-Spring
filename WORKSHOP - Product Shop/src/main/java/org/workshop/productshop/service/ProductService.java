package org.workshop.productshop.service;

import org.workshop.productshop.domain.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {

    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProduct();
}
