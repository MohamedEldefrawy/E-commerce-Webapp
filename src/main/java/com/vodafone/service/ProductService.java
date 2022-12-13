package com.vodafone.service;

import com.vodafone.model.Product;
import com.vodafone.repository.product.ProductRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
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

    public boolean delete(Long id) {
        return this.productRepository.delete(id);
    }

    public Product getByName(String name) {
        return this.productRepository.getByName(name);
    }

    public List<Product> getByCategory(String category) {
        return this.productRepository.getByCategory(category);
    }


    public List<Product> getByPrice(double price) {
        return this.productRepository.getByPrice(price);
    }

    public List<Product> getByRate(float rate) {
        return this.productRepository.getByRate(rate);
    }

    public List<Product> getByPriceRange(double low, double high) {
        return this.productRepository.getByPriceRange(low, high);
    }
    public List<Product> getAvailableProducts() {
        return this.productRepository.getAvailableProducts();
    }

}
