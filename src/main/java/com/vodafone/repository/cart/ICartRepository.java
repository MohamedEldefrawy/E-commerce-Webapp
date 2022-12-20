package com.vodafone.repository.cart;

import com.vodafone.model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface ICartRepository extends CrudRepository<Cart,Long> { }
