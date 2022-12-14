package com.vodafone.service;

import com.vodafone.exception.product.GetProductException;
import com.vodafone.model.Product;
import com.vodafone.repository.product.IProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Product getByName(String name) throws GetProductException {
        Optional<List<Product>> optionalProducts = this.productRepository.getByName(name);
        if (optionalProducts.isPresent() && optionalProducts.get().size() > 0)
            return optionalProducts.get().get(0);
        else
            throw new GetProductException("No product found with name: " + name);

    }


    public List<Product> getByCategory(String category) {
        return this.productRepository.getByCategory(category);
    }

    public List<Product> getAvailableProducts() {
        return this.productRepository.getAvailableProducts();
    }
}
