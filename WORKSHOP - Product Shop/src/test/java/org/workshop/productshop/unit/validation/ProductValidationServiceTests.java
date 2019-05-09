package org.workshop.productshop.unit.validation;

import org.junit.*;
import org.workshop.productshop.domain.entities.Product;
import org.workshop.productshop.validation.ProductValidationService;
import org.workshop.productshop.validation.implementations.ProductValidationServiceImpl;

import static org.junit.Assert.assertFalse;

public class ProductValidationServiceTests {
    private ProductValidationService service;

    @Before
    public void setupTest(){
        service = new ProductValidationServiceImpl();
    }

    @Test
    public void isValid_whenNull_false(){
        boolean result = service.isValid(null);
        assertFalse(result);
    }

    @Test
    public void isValid_whenValid_true(){
        boolean result = service.isValid(new Product());
        assertFalse(result);
    }
}
