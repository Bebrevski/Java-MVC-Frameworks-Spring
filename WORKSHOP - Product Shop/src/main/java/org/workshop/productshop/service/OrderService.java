package org.workshop.productshop.service;

import org.workshop.productshop.domain.models.service.OrderServiceModel;

import java.util.List;

public interface OrderService {
    void createOrder(String productId, String name) throws Exception;

    List<OrderServiceModel> findAllOrders();

    List<OrderServiceModel> findOrdersByCustomerName(String username);
}
