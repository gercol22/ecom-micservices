package com.dgm.store.product;

import com.dgm.store.product.entity.Category;
import com.dgm.store.product.entity.Product;
import com.dgm.store.product.repository.ProductRepository;
import com.dgm.store.product.service.ProductService;
import com.dgm.store.product.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    ProductRepository productRepository;

    ProductService productService;

    @BeforeEach
    public void setup () {
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productRepository);
        Product computer = Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .price(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5"))
                .build();

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(computer));

        Mockito.when(productRepository.save(computer)).thenReturn(computer);


    }

    @Test
    public void whenValidGetId_thenReturnProduct () {
        Optional<Product> optFound = productService.getProduct(1L);
        if (optFound.isPresent()) {
            Product found = optFound.get();
            Assertions.assertThat(found.getName()).isEqualTo("computer");
        }
    }

    @Test
    public void whenValidUpdateStock_thenReturnNewStock () {
        Product newStock = productService.updateStock(1L, Double.parseDouble("8"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(Double.parseDouble("13"));
    }

}
