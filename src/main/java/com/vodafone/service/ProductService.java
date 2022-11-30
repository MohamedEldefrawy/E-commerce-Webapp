package com.vodafone.service;

import com.vodafone.model.Product;
import com.vodafone.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean create(Product product) {
        return this.productRepository.create(product);
    }

    public boolean update(Long id, Product newProduct) {
        return this.productRepository.update(id, newProduct);
    }

    public List<Product> getAll() {
        return this.productRepository.getAll();
    }

    public Product get(Long id) {
        return this.productRepository.get(id);
    }
}
