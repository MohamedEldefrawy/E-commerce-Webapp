//package com.vodafone.Ecommerce.service;
//
//import com.vodafone.Ecommerce.exception.product.CreateProductException;
//import com.vodafone.Ecommerce.exception.product.GetProductException;
//import com.vodafone.Ecommerce.model.Product;
//import com.vodafone.Ecommerce.repository.product.IProductRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@AllArgsConstructor
//public class ProductService {
//    private IProductRepository productRepository;
//
//    public boolean create(Product product) throws CreateProductException {
//
//        Optional<Long> optionalLong = this.productRepository.create(product);
//        if (optionalLong.isPresent())
//            return true;
//        throw new CreateProductException("Failed to create new product");
//    }
//
//    public boolean update(Long id, Product newProduct) throws GetProductException {
//        Optional<Product> productOptional = this.productRepository.getById(id);
//        if (productOptional.isPresent())
//            return this.productRepository.update(id, newProduct);
//        throw new GetProductException("No product found with id: " + id);
//    }
//
//    public List<Product> getAll() throws GetProductException {
//        Optional<List<Product>> optionalProducts = this.productRepository.getAll();
//        if (optionalProducts.isPresent())
//            return optionalProducts.get();
//        throw new GetProductException("No products found");
//    }
//
//    public Product getById(Long id) throws GetProductException {
//        Optional<Product> productOptional = this.productRepository.getById(id);
//        if (productOptional.isPresent())
//            return productOptional.get();
//        throw new GetProductException("No product found with id: " + id);
//    }
//
//    public boolean delete(Long id) throws GetProductException {
//        Optional<Product> productOptional = this.productRepository.getById(id);
//        if (productOptional.isPresent())
//            return this.productRepository.delete(id);
//        throw new GetProductException("no product found with id: " + id);
//    }
//
//    public Product getByName(String name) throws GetProductException {
//        Optional<List<Product>> optionalProducts = this.productRepository.getByName(name);
//        if (optionalProducts.isPresent() && !optionalProducts.get().isEmpty())
//            return optionalProducts.get().get(0);
//        throw new GetProductException("No product found with name: " + name);
//    }
//
//    public List<Product> getByCategory(String category) throws GetProductException {
//        Optional<List<Product>> optionalProducts = this.productRepository.getByCategory(category);
//        if (optionalProducts.isPresent())
//            return optionalProducts.get();
//        throw new GetProductException("No products found in category: " + category);
//    }
//
//    public List<Product> getAvailableProducts() {
//        Optional<List<Product>> optionalProducts = this.productRepository.getAvailableProducts();
//        if (optionalProducts.isPresent() && !optionalProducts.get().isEmpty())
//            return optionalProducts.get();
//
//        throw new GetProductException("No products found");
//    }
//}
