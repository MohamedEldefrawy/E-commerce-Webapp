package service;

import com.vodafone.exception.NullOrderException;
import com.vodafone.model.Customer;
import com.vodafone.model.Order;
import com.vodafone.model.OrderItem;
import com.vodafone.model.Product;
import com.vodafone.repository.order.OrderRepository;
import com.vodafone.service.OrderService;
import org.junit.jupiter.api.Test;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    private static final OrderRepository orderRepositoryMock =mock(OrderRepository.class); ;
    private static final OrderService orderService =new OrderService(orderRepositoryMock);
    @Test
    void create_NullOrder_returnException(){
        ///Arrange
        Order entity =null;
        //Act
        when(orderRepositoryMock.create(entity)).thenThrow(new NullOrderException("Null Order Provided"));
        //Assert
        assertThrows(NullOrderException.class,()->orderService.create(entity));
    }
    @Test
    void create_EmptyOrder_returnFalse(){
        ///Arrange
        Order entity =new Order();
        //Act
        when(orderRepositoryMock.create(entity)).thenReturn(Optional.empty());
        //Assert
        assertFalse(orderService.create(entity));
    }

    @Test
     void create_FullyOrder_returnTrue(){
        ///Arrange
        Customer customer = new Customer();
        customer.setUserName("Neimat");
        customer.setEmail("neimat.soliman.ismail@gmail.com");

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(new Product());
        orderItem.setQuantity(6);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);

        Order entity =new Order();
        entity.setCustomer(customer);
        entity.setOrderItems(orderItems);

        //Act
        when(orderRepositoryMock.create(entity)).thenReturn(Optional.of(any(Long.class)));
        //Assert
        assertTrue(orderService.create(entity));
    }


}
