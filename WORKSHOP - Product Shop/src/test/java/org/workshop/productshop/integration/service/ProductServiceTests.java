package org.workshop.productshop.integration.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.workshop.productshop.domain.entities.Product;
import org.workshop.productshop.domain.models.service.CategoryServiceModel;
import org.workshop.productshop.domain.models.service.ProductServiceModel;
import org.workshop.productshop.repository.ProductRepository;
import org.workshop.productshop.service.ProductService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {
    @Autowired
    private ProductService service;

    @MockBean
    private ProductRepository mockProductRepository;

    @Test
    public void createProduct_whenValid_productCreated(){
        ProductServiceModel product = new ProductServiceModel();
        product.setCategories(List.of(new CategoryServiceModel()));

        when(mockProductRepository.save(any()))
                .thenReturn(new Product());

        service.createProduct(product);
        verify(mockProductRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void createProduct_whenNull_throw(){
        service.createProduct(null);
        verify(mockProductRepository)
                .save(any());
    }
}
