package org.workshop.productshop.service;

import org.workshop.productshop.domain.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {

    ProductServiceModel createProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProduct();

    ProductServiceModel findProductById(String id);

    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel);

    void deleteProduct(String id);
}
