package com.vodafone.controller.rest;

import com.vodafone.exception.EntityNotFoundException;
import com.vodafone.exception.NullIdException;
import com.vodafone.exception.cart.NegativeQuantityException;
import com.vodafone.exception.cart.NullCartItemException;
import com.vodafone.model.Cart;
import com.vodafone.model.Customer;
import com.vodafone.model.Order;
import com.vodafone.model.CartItem;
import com.vodafone.service.CartService;
import com.vodafone.service.CustomerService;
import com.vodafone.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/customers")
public class CustomerControllerRest {

    private CustomerService customerService;
    private OrderService orderService;
    private CartService cartService;

    //Get
    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        List<Customer> customers = customerService.getAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        Customer customer = customerService.findCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable("email") String email) {
        Customer customer = customerService.findCustomerByEmail(email);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("{username}")
    public ResponseEntity<Customer> getCustomerByUserName(@PathVariable("username") String username) {
        Customer customer = customerService.findCustomerByUserName(username);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping({"orders/{id}"})
    public ResponseEntity<List<Order>> getCustomerOrders(@PathVariable("id") Long customerId) {
        return new ResponseEntity<>(orderService.getByCustomerId(customerId), HttpStatus.OK);
    }

    @GetMapping("{id}/cart")
    public ResponseEntity<Cart> getCustomerCart(@PathVariable("id") Long customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        return new ResponseEntity<>(cartService.get(customer.getId()), HttpStatus.OK);
    }

    //Post
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customerService.create(customer);
        if (customerService.findCustomerById(customer.getId()) != null) {
            return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(customer, HttpStatus.BAD_REQUEST);
    }

    //Put
    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long customerId, @RequestBody Customer customer) {
        customer = customerService.update(customerId, customer);
        return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);
    }

    @PutMapping("{email}/activate")
    public ResponseEntity<String> updateCustomerStatus(@PathVariable("email") String email) {
        if (customerService.updateStatusActivated(email))
            return new ResponseEntity<>("Customer is activated successfully", HttpStatus.OK);
        return new ResponseEntity<>("Customer's status is not updated", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("{email}/reset")
    public ResponseEntity<String> resetCustomerPassword(@PathVariable("email") String email, @RequestBody String password) {
        if (customerService.resetPassword(email, password))
            return new ResponseEntity<>("Customer's password is reset successfully", HttpStatus.OK);
        return new ResponseEntity<>("Customer's password can't be updated", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("{id}/cart-clear")
    public ResponseEntity<Customer> clearCustomerCart(@PathVariable("id") Long customerId) {
        try {
            Customer customer = customerService.findCustomerById(customerId);
            if (cartService.clearCart(customer.getCart().getId()))
                return new ResponseEntity<>(customer, HttpStatus.OK);
            return new ResponseEntity<>(customer, HttpStatus.BAD_REQUEST);
        } catch (NullIdException | EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @PutMapping("{id}/cart/add-item")
    public ResponseEntity<Customer> addItemToCustomerCart(@PathVariable("id") Long customerId, @RequestBody CartItem item) {
        Customer customer = customerService.findCustomerById(customerId);
        try {
            if (cartService.addItem(customer.getCart().getId(), item) > 0)
                return new ResponseEntity<>(customer, HttpStatus.OK);
            return new ResponseEntity<>(customer, HttpStatus.BAD_REQUEST);
        } catch (NullIdException | NullCartItemException | EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @PutMapping("{id}/cart/{itemId}")
    public ResponseEntity<Customer> removeItemToCustomerCart(@PathVariable("id") Long customerId, @PathVariable Long itemId) {
        Customer customer = customerService.findCustomerById(customerId);
        try {
            if (cartService.removeItem(customer.getCart().getId(), itemId))
                return new ResponseEntity<>(customer, HttpStatus.OK);
            return new ResponseEntity<>(customer, HttpStatus.BAD_REQUEST);
        } catch (NullIdException | EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @PutMapping("{id}/cart/quantity/{itemId}/{quantity}")
    public ResponseEntity<Customer> setProductQuantity(@PathVariable("id") Long customerId, @PathVariable("itemId") Long itemId, @PathVariable("quantity") int quantity) {
        Customer customer = customerService.findCustomerById(customerId);
        try {
            if (cartService.setProductQuantity(customer.getCart().getId(), itemId, quantity) > 0)
                return new ResponseEntity<>(customer, HttpStatus.OK);
            return new ResponseEntity<>(customer, HttpStatus.BAD_REQUEST);
        } catch (NullIdException | NegativeQuantityException | EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Delete
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long customerId) {
        customerService.delete(customerId);
        if (customerService.findCustomerById(customerId) == null)
            return new ResponseEntity<>("Customer is deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>("Customer can't be deleted", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
