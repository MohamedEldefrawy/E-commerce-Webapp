package com.vodafone.service;

import com.vodafone.exception.product.CreateProductException;
import com.vodafone.exception.product.GetProductException;
import com.vodafone.model.Product;
import com.vodafone.repository.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public Long create(Product product) throws CreateProductException {
        try {
            Product createdProduct = this.productRepository.save(product);
            return createdProduct.getId();
        } catch (IllegalArgumentException e) {
            throw new CreateProductException("Failed to create new product");
        }
    }

    public Long update(Product updatedProduct) throws GetProductException {
        try {
            Product updaatedProduct = this.productRepository.save(updatedProduct);
            return updaatedProduct.getId();
        } catch (IllegalArgumentException e) {
            throw new GetProductException("No product found with id: " + updatedProduct.getId());
        }
    }

    public List<Product> getAll() throws GetProductException {
        List<Product> products = this.productRepository.findAll();
        if (products.size() > 0)
            return products;
        throw new GetProductException("No products found");
    }

    public Product getById(Long id) throws GetProductException {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if (productOptional.isPresent())
            return productOptional.get();
        throw new GetProductException("No product found with id: " + id);
    }

    public boolean delete(Long id) throws GetProductException {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if (productOptional.isPresent()) {
            this.productRepository.delete(productOptional.get());
            return true;
        }
        throw new GetProductException("no product found with id: " + id);
    }

    public List<Product> getByName(String name) throws GetProductException {
        Optional<List<Product>> optionalProducts = this.productRepository.findAllByName(name);
        if (optionalProducts.isPresent() && !optionalProducts.get().isEmpty())
            return optionalProducts.get();
        throw new GetProductException("No product found with name: " + name);
    }

    public List<Product> getByCategory(String category) throws GetProductException {
        Optional<List<Product>> optionalProducts = this.productRepository.findAllByCategory(category);
        if (optionalProducts.isPresent())
            return optionalProducts.get();
        throw new GetProductException("No products found in category: " + category);
    }

    public List<Product> getAvailableProducts() {
        Optional<List<Product>> optionalProducts = this.productRepository.findAllByDeletedIsFalse();
        if (optionalProducts.isPresent() && !optionalProducts.get().isEmpty())
            return optionalProducts.get();

        throw new GetProductException("No products found");
    }
}
