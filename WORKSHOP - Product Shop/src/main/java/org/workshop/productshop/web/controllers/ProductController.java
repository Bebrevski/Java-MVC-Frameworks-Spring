package org.workshop.productshop.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.workshop.productshop.domain.models.binding.ProductAddBindingModel;
import org.workshop.productshop.domain.models.service.ProductServiceModel;
import org.workshop.productshop.domain.models.view.ProductAllViewModel;
import org.workshop.productshop.service.CategoryService;
import org.workshop.productshop.service.CloudinaryService;
import org.workshop.productshop.service.ProductService;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProduct() {
        return super.view("product/add-product");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProductConfirm(@ModelAttribute ProductAddBindingModel model) throws IOException {
        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        productServiceModel.setCategories(
                this.categoryService.findAllCategories()
                        .stream()
                        .filter(c -> model.getCategories().contains(c.getId()))
                        .collect(Collectors.toList())
        );

        productServiceModel.setImageUrl(this.cloudinaryService.uploadImage(model.getImage()));
        this.productService.addProduct(productServiceModel);
        return super.redirect("/products/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allProducts(ModelAndView modelAndView) {
        modelAndView.addObject(
                "products",
                this.productService.findAllProduct()
                        .stream()
                        .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                        .collect(Collectors.toList())
        );

        return super.view("product/all-products", modelAndView);
    }
}
