package com.vodafone.repository.product;

import com.vodafone.model.Product;
import com.vodafone.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface IProductRepository extends Repository<Long, Product> {
    Optional<List<Product>> getByName(String name);

    Optional<List<Product>> getByCategory(String category);

    List<Product> getAvailableProducts();
}
