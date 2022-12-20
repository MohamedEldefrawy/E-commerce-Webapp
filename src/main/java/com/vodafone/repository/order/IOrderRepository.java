package com.vodafone.repository.order;

import com.vodafone.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<Order,Long> {
    Optional<List<Order>> findAllByCustomerId(Long customerId);

//    List<Order> getByCustomerId(Long customerId);

}
