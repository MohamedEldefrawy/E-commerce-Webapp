package com.vodafone.repository.order;

import com.vodafone.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends CrudRepository<Order,Long> {
    Optional<List<Order>> findByCustomerId(Long customerId);

//    List<Order> getByCustomerId(Long customerId);

}
