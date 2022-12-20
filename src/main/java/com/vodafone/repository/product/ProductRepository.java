package com.vodafone.repository.product;

import com.vodafone.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findAllByName(String name);

    Optional<List<Product>> findAllByCategory(String category);

    Optional<List<Product>> findAllByNameAndCategory(String name, String category);

    Optional<List<Product>> findAllByDeletedIsFalse();
}
