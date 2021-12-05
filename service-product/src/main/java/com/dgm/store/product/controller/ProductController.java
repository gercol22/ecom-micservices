package com.dgm.store.product.controller;

import com.dgm.store.product.entity.Category;
import com.dgm.store.product.entity.Product;
import com.dgm.store.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping (value = "/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam (name= "categoryId", required = false) Long categoryId) {
        List<Product> products = new ArrayList<>();

        if (categoryId == null) {
            products = productService.listAllProduct();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if (products.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        }


        return ResponseEntity.ok(products);
    }

    @GetMapping (value = "/{id}")
    public ResponseEntity<Product> getProduct (@PathVariable("id") Long id) {
        Optional<Product> optProduct = productService.getProduct(id);
        if (!optProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product product = optProduct.get();
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct (@Valid @RequestBody Product product, BindingResult result) {

        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Product productCreated = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @PutMapping (value = "/{id}")
    public ResponseEntity<Product> updateProduct (@PathVariable("id") Long id, @RequestBody Product product) {
        product.setId(id);
        Product productUpdated = productService.updateProduct(product);
        Optional<Product> optProduct = Optional.ofNullable(productUpdated);

        if (!optProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optProduct.get());
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity<Product> deleteProduct (@PathVariable("id") Long id) {

        Product productDelete = productService.deleteProduct(id);
        Optional<Product> optProduct = Optional.ofNullable(productDelete);

        if (!optProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optProduct.get());
    }

    @GetMapping (value = "/{id}/stock")
    public ResponseEntity<Product> updateStock (@PathVariable("id") Long id, @RequestParam(name = "qty") Double qty) {
        Product productUpdated = productService.updateStock(id, qty);
        Optional<Product> optProduct = Optional.ofNullable(productUpdated);

        if (!optProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optProduct.get());
    }

    private String formatMessage (BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream().map(fieldError -> {
            Map<String, String> error = new HashMap<>();
            error.put(fieldError.getCode(), fieldError.getDefaultMessage());
            return error;
        }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .message(errors).build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonStr;
    }

}
