package com.vodafone.service;

import com.vodafone.model.Product;
import com.vodafone.repository.product.IProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private IProductRepository productRepository;

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

    public boolean delete(Long id) {
        return this.productRepository.delete(id);
    }

    public Product getByName(String name) {
        return this.productRepository.getByName(name);
    }

    public List<Product> getByCategory(String category) {
        return this.productRepository.getByCategory(category);
    }

    public List<Product> getAvailableProducts() {
        return this.productRepository.getAvailableProducts();
    }
}
