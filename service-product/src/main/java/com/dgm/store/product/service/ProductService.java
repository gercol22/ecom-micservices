package com.dgm.store.product.service;

import com.dgm.store.product.entity.Category;
import com.dgm.store.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public List<Product> listAllProduct();
    public Optional<Product> getProduct(Long id);
    public Product createProduct(Product product);
    public Product updateProduct(Product product);
    public  Product deleteProduct(Long id);
    public List<Product> findByCategory(Category category);
    public Product updateStock(Long id, Double quantity);
}
