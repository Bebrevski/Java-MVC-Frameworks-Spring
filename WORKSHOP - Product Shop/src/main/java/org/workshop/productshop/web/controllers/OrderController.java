package org.workshop.productshop.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.modelmapper.ModelMapper;

import org.workshop.productshop.domain.models.service.OrderServiceModel;
import org.workshop.productshop.domain.models.service.ProductServiceModel;
import org.workshop.productshop.domain.models.view.OrderViewModel;
import org.workshop.productshop.domain.models.view.ProductDetailsViewModel;
import org.workshop.productshop.service.OrderService;
import org.workshop.productshop.service.ProductService;


@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final OrderService orderService;

    @Autowired
    public OrderController(ProductService productService, ModelMapper modelMapper, OrderService orderService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.orderService = orderService;
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView orderProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductDetailsViewModel viewModel = this.modelMapper.map(productServiceModel, ProductDetailsViewModel.class);
        modelAndView.addObject("product", viewModel);
        return view("order/product", modelAndView);
    }

    @GetMapping("/all")
    public ModelAndView getAllOrders(ModelAndView modelAndView){
        List<OrderViewModel> viewModels = orderService.findAllOrders()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("orders", viewModels);

        return view("order/all-orders", modelAndView);
    }
}
