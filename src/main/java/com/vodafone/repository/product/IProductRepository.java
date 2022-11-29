package com.vodafone.repository.product;

import com.vodafone.model.Product;
import com.vodafone.repository.Repository;

import java.util.List;

public interface IProductRepository extends Repository<Product> {
    Product getByName(String name);

    List<Product> getByCategory(String category);
}
