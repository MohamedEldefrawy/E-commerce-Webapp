package com.vodafone.repository.product;

import com.vodafone.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IProductRepository extends JpaRepository<Product, Long> {
//    Optional<List<Product>> getByName(String name);
//
//    Optional<List<Product>> getByCategory(String category);
//
//    Optional<List<Product>> getAvailableProducts();

    Optional<List<Product>> findByName(String name);

    Optional<List<Product>> findAllByCategory(String category);

    Optional<List<Product>> findAllByDeletedEquals(boolean isDeleted);
}
