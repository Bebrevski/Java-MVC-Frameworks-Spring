package org.workshop.productshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workshop.productshop.domain.entities.Order;
import org.workshop.productshop.domain.entities.Product;
import org.workshop.productshop.domain.entities.User;
import org.workshop.productshop.domain.models.service.OrderServiceModel;
import org.workshop.productshop.domain.models.service.UserServiceModel;
import org.workshop.productshop.repository.OrderRepository;
import org.workshop.productshop.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createOrder(String productId, String name) {
        UserServiceModel userServiceModel = this.userService.findUserByUsername(name);

        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id!"));

        User user = new User();
        user.setId(userServiceModel.getId());

        Order order = new Order();
        order.setProduct(product);
        order.setUser(user);

        this.orderRepository.save(order);
    }

    @Override
    public List<OrderServiceModel> findAllOrders() {
        return this.orderRepository.findAll()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }
}
