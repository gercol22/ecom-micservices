package com.dgm.store.product.service;

import com.dgm.store.product.entity.Category;
import com.dgm.store.product.entity.Product;
import com.dgm.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> optPrdDB = getProduct(product.getId());
        if (!optPrdDB.isPresent()) {
            return null;
        }

        Product productBD = optPrdDB.get();
        productBD.setName(product.getName());
        productBD.setDescription(product.getDescription());
        productBD.setCategory(product.getCategory());
        productBD.setPrice(product.getPrice());
        productBD.setStatus("UPDATED");

        return productRepository.save(productBD);
    }

    @Override
    public Product deleteProduct(Long id) {
        Optional<Product> optPrdDB = getProduct(id);
        if (!optPrdDB.isPresent()) {
            return null;
        }

        Product productBD = optPrdDB.get();
        productBD.setStatus("DELETE");

        return productRepository.save(productBD);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Optional<Product> optPrdDB = getProduct(id);
        if (!optPrdDB.isPresent()) {
            return null;
        }

        Product productBD = optPrdDB.get();
        Double newStock = productBD.getStock() + quantity;
        productBD.setStock(newStock);

        return productRepository.save(productBD);
    }
}
