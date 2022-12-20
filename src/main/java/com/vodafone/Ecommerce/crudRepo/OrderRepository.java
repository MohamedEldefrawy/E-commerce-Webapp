package com.vodafone.Ecommerce.crudRepo;

import org.springframework.data.repository.CrudRepository;
import com.vodafone.Ecommerce.model.Order;

public interface OrderRepository extends CrudRepository<Order,Long> {
    Iterable<Order> findAllByCustomerId(Long id);

}
