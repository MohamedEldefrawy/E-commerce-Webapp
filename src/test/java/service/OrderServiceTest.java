package service;

import com.vodafone.exception.NullIdException;
import com.vodafone.exception.NullOrderException;
import com.vodafone.model.Customer;
import com.vodafone.model.Order;
import com.vodafone.model.OrderItem;
import com.vodafone.model.Product;
import com.vodafone.repository.order.OrderRepository;
import com.vodafone.service.OrderService;
import org.junit.jupiter.api.Test;


import java.util.*;

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

    @Test
    void update_NullOrder_rightId_returnException(){
        ///Arrange
        Customer customer = new Customer();
        customer.setUserName("Neimat");
        customer.setEmail("neimat.soliman.ismail@gmail.com");

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(new Product());
        orderItem.setQuantity(6);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);

        Order oldOrder =new Order();
        Order updateOrder = null;
        oldOrder.setCustomer(customer);
        oldOrder.setOrderItems(orderItems);
        //Act
        when(orderRepositoryMock.update(1L,updateOrder)).thenThrow(new NullOrderException("Null Order Provided"));
        //Assert
        assertThrows(NullOrderException.class,()->orderService.update(1L,updateOrder));
    }

    @Test
    void update_nullId_rightOrder__returnFalse(){
        ///Arrange
        Customer customer = new Customer();
        customer.setUserName("Neimat");
        customer.setEmail("neimat.soliman.ismail@gmail.com");

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(new Product());
        orderItem.setQuantity(6);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);

        Order oldOrder =new Order();
        oldOrder.setCustomer(customer);
        oldOrder.setOrderItems(orderItems);


        Customer updatedCustomer = new Customer();
        updatedCustomer.setUserName("Nei");
        updatedCustomer.setEmail("neimat.soliman.ismail@gmail.com");
        Order updateOrder = oldOrder;
        updateOrder.setCustomer(updatedCustomer);
        //Act
        when(orderRepositoryMock.update(null,updateOrder)).thenThrow(new NullIdException("Null order id is provided"));
        //Assert
        assertThrows(NullIdException.class,()->orderService.update(null,updateOrder));
    }

    @Test
    void update_rightOrder_rightId_returnTrue(){
        ///Arrange
        Customer customer = new Customer();
        customer.setUserName("Neimat");
        customer.setEmail("neimat.soliman.ismail@gmail.com");

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(new Product());
        orderItem.setQuantity(6);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);

        Order order =new Order();
        order.setCustomer(customer);
        order.setOrderItems(orderItems);

        //Act
        when(orderRepositoryMock.update(1L,order)).thenReturn(true);
        //Assert
        assertTrue(orderService.update(1L,order));

    }

    @Test
    void update_rightOrder_wrongId_returnFalse(){
        ///Arrange
        Customer customer = new Customer();
        customer.setUserName("Neimat");
        customer.setEmail("neimat.soliman.ismail@gmail.com");

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(new Product());
        orderItem.setQuantity(6);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);

        Order order =new Order();
        order.setCustomer(customer);
        order.setOrderItems(orderItems);

        //Act
        when(orderRepositoryMock.update(1L,order)).thenReturn(true);
        //Assert
        assertFalse(orderService.update(2L,order));

    }

    @Test
    void delete_nullId_expectException(){
        //Act
        when(orderRepositoryMock.delete(null)).thenThrow(new NullIdException("Null order id is provided"));
        //Assert
        assertThrows(NullIdException.class,()->orderService.delete(null));

    }
    @Test
    void delete_wrongId_returnFalse(){
        //Act
        when(orderRepositoryMock.delete(1L)).thenReturn(true);
        //Assert
        assertFalse(orderService.delete(2L));
    }
    @Test
    void delete_rightId_returnTrue(){
        //Act
        when(orderRepositoryMock.delete(1L)).thenReturn(true);
        //Assert
        assertTrue(orderService.delete(1L));
    }

    @Test
    void get_nullId_expectException(){
        //Act
        when(orderRepositoryMock.getById(null)).thenThrow(new NullIdException("Null order id is provided"));
        //Assert
        assertThrows(NullIdException.class,()->orderService.get(null));

    }
    @Test
    void get_wrongId_returnFalse(){
        ///Arrange
        Customer customer = new Customer();
        customer.setUserName("Neimat");
        customer.setEmail("neimat.soliman.ismail@gmail.com");

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(new Product());
        orderItem.setQuantity(6);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);

        Order order =new Order();
        order.setCustomer(customer);
        order.setOrderItems(orderItems);
        //Act
        when(orderRepositoryMock.getById(1L)).thenReturn(Optional.of(order));
        //Assert
        assertThrows(NullIdException.class,()-> orderService.get(2L));
    }
    @Test
    void get_rightId_returnTrue(){
        ///Arrange
        Customer customer = new Customer();
        customer.setUserName("Neimat");
        customer.setEmail("neimat.soliman.ismail@gmail.com");

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(new Product());
        orderItem.setQuantity(6);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);

        Order order =new Order();
        order.setCustomer(customer);
        order.setOrderItems(orderItems);
        //Act
        when(orderRepositoryMock.getById(1L)).thenReturn(Optional.of(order));
        //Assert
        assertEquals(orderService.get(1L),order);
    }

    @Test
    void getAll_returnTrue(){
        ///Arrange
        List<Order> orderList = new ArrayList<>();

        Customer customer = new Customer();
        customer.setUserName("Neimat");
        customer.setEmail("neimat.soliman.ismail@gmail.com");

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(new Product());
        orderItem.setQuantity(6);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);

        Order order =new Order();
        order.setCustomer(customer);
        order.setOrderItems(orderItems);
        orderList.add(order);
        //Act
        when(orderRepositoryMock.getAll()).thenReturn(Optional.of(orderList));
        //Assert
        assertEquals(orderService.getAll(),orderList);
    }

}
