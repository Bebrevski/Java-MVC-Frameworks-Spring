package org.workshop.productshop.integration.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.workshop.productshop.domain.entities.Order;
import org.workshop.productshop.domain.entities.Product;
import org.workshop.productshop.domain.entities.User;
import org.workshop.productshop.domain.models.service.OrderServiceModel;
import org.workshop.productshop.domain.models.service.UserServiceModel;
import org.workshop.productshop.repository.OrderRepository;
import org.workshop.productshop.repository.ProductRepository;
import org.workshop.productshop.service.OrderService;
import org.workshop.productshop.service.UserService;
import org.workshop.productshop.validation.ProductValidationService;
import org.workshop.productshop.validation.UserValidationService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @MockBean
    OrderRepository mockOrderRepository;
    @MockBean
    UserValidationService mockUserValidationService;
    @MockBean
    UserService mockUserService;
    @MockBean
    ProductValidationService mockProductValidationService;
    @MockBean
    ProductRepository mockProductRepository;

    private List<Order> orders;

    @Before
    public void setupTest() {
        orders = new ArrayList<>();

        when(mockOrderRepository.findAll())
                .thenReturn(orders);
    }

    @Test
    public void findAllOrders_when1Order_return1Order() {
        String customer = "Test customer";
        String productImageUrl = "http://image.url";
        String productName = "product 1";
        BigDecimal productPrice = BigDecimal.valueOf(1.34);

        Order order = new Order();
        order.setUser(new User() {{
            setUsername(customer);
        }});
        order.setProduct(new Product() {{
            setImageUrl(productImageUrl);
            setName(productName);
            setPrice(productPrice);
        }});

        orders.add(order);

        var result = orderService.findAllOrders();
        OrderServiceModel orderResult = result.get(0);

        assertEquals(1, result.size());
        assertEquals(customer, orderResult.getCustomer());
        assertEquals(productName, orderResult.getName());
        assertEquals(productImageUrl, orderResult.getImageUrl());
        assertEquals(productPrice, orderResult.getPrice());
    }

    @Test
    public void findAllOrders_whenNoOrder_returnNoOrder() {
        orders.clear();
        var result = orderService.findAllOrders();
        assertTrue(result.isEmpty());
    }

    @Test
    public void createOrder_whenUserAndProductAreValid_orderCreate() throws Exception {
        when(mockUserValidationService.isValid(any()))
                .thenReturn(true);
        when(mockProductValidationService.isValid(any(Product.class)))
                .thenReturn(true);
        when(mockUserService.findUserByUsername(any()))
                .thenReturn(new UserServiceModel());
        when(mockProductRepository.findById(any()))
                .thenReturn(java.util.Optional.of(new Product()));

        orderService.createOrder("", "");

        verify(mockOrderRepository).save(any());
    }

    @Test(expected = Exception.class)
    public void createOrder_whenUserIsValidAndProductIsNotValid_throw() throws Exception {
        when(mockUserValidationService.isValid(any()))
                .thenReturn(true);
        when(mockProductValidationService.isValid(any(Product.class)))
                .thenReturn(false);

        orderService.createOrder("", "");
    }

    @Test(expected = Exception.class)
    public void createOrder_whenUserIsNotValidAndProductIsValid_orderCreate() throws Exception {
        when(mockUserValidationService.isValid(any()))
                .thenReturn(false);
        when(mockProductValidationService.isValid(any(Product.class)))
                .thenReturn(true);

        orderService.createOrder("", "");
    }

    @Test(expected = Exception.class)
    public void createOrder_whenUserAndProductAreNotValid_orderCreate() throws Exception {
        when(mockUserValidationService.isValid(any()))
                .thenReturn(false);
        when(mockProductValidationService.isValid(any(Product.class)))
                .thenReturn(false);

        orderService.createOrder("", "");
    }
}
