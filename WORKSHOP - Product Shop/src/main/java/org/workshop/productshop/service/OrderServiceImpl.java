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
import org.workshop.productshop.validation.ProductValidationService;
import org.workshop.productshop.validation.UserValidationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    private final ProductValidationService productValidationService;
    private final UserValidationService userValidationService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, ProductRepository productRepository, ModelMapper modelMapper, ProductValidationService productValidationService, UserValidationService userValidationService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.productValidationService = productValidationService;
        this.userValidationService = userValidationService;
    }

    @Override
    public void createOrder(String productId, String name) throws Exception {
        UserServiceModel userServiceModel = this.userService.findUserByUsername(name);

        if (!userValidationService.isValid(userServiceModel)) {
            throw new Exception("Invalid id!");
        }

        Product product = this.productRepository.findById(productId)
                .filter(productValidationService::isValid)
                .orElseThrow(Exception::new);

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

    @Override
    public List<OrderServiceModel> findOrdersByCustomerName(String username) {
        return this.orderRepository.findAllByUser_Username(username)
                .stream()
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }
}
