package org.workshop.productshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workshop.productshop.domain.entities.Product;
import org.workshop.productshop.domain.models.service.ProductServiceModel;
import org.workshop.productshop.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel addProduct(ProductServiceModel productServiceModel) {
        Product product = this.modelMapper.map(productServiceModel, Product.class);

        return this.modelMapper.map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }
}
