package org.workshop.productshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workshop.productshop.domain.entities.Category;
import org.workshop.productshop.domain.entities.Product;
import org.workshop.productshop.domain.models.service.ProductServiceModel;
import org.workshop.productshop.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel addProduct(ProductServiceModel productServiceModel) {
        Product product = this.modelMapper.map(productServiceModel, Product.class);

        return this.modelMapper.map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllProduct() {
        return this.productRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductById(String id) {
        return this.productRepository
                .findById(id)
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .orElseThrow(() -> new IllegalArgumentException("Invalid id!"));
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id!"));

        productServiceModel.setCategories(
                this.categoryService.findAllCategories()
                .stream()
                .filter(c -> productServiceModel.getCategories().contains(c.getId()))
                .collect(Collectors.toList())
        );

        product.setName(productServiceModel.getName());
        product.setDescription(productServiceModel.getDescription());
        product.setPrice(productServiceModel.getPrice());
        product.setCategories(
                productServiceModel.getCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, Category.class))
                .collect(Collectors.toList())
        );

        return this.modelMapper.map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }

    @Override
    public void deleteProduct(String id) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id!"));

        this.productRepository.delete(product);
    }
}
