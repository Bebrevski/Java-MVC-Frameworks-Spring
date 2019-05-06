package org.workshop.productshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.workshop.productshop.domain.models.rest.ProductOrderRequstModel;
import org.workshop.productshop.service.OrderService;

import java.security.Principal;

@RestController
@RequestMapping("/api/order")
public class OrderApiController {

    private final OrderService orderService;

    @Autowired
    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/submit")
    public void submitOrder(@RequestBody ProductOrderRequstModel model, Principal principal) {
        String name = principal.getName();
        this.orderService.createOrder(model.getId(), name);
    }
}
