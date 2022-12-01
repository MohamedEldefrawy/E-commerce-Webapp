package com.vodafone.controller;

import com.vodafone.model.*;
import com.vodafone.model.dto.CreateUser;
import com.vodafone.service.CartService;
import com.vodafone.service.CustomerService;
import com.vodafone.service.OrderService;
import com.vodafone.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    CustomerService customerService;
    OrderService orderService;
    ProductService productService;
    CartService cartService;

    @PostMapping
    public boolean create(Customer customer) {
        return customerService.create(customer);
    }

    @PutMapping
    public boolean update(Long id, Customer updatedCustomer) {
        return customerService.update(id, updatedCustomer);
    }

    @DeleteMapping
    public boolean delete(Long id) {
        return customerService.delete(id);
    }

    @GetMapping("{id}")
    public Customer get(@PathVariable("id") Long id) {
        return customerService.get(id);
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @GetMapping("reset.htm")
    public String resetPassword(Model model) {
        customerService.resetPassword(Objects.requireNonNull(model.getAttribute("email")).toString(), Objects.requireNonNull(model.getAttribute("password")).toString());
        model.addAttribute("user", new CreateUser());
        return "resetPassword";
    }

    @GetMapping("products/rate")
    public String getProductsByRate(float rate) {
        List<Product> products = productService.getByRate(rate);
        return null;
    }

    @GetMapping("products/price")
    public String getProductsByPrice(double price) {
        List<Product> products = productService.getByPrice(price);
        return null;
    }

    @GetMapping("products/range")
    public String getProductsBetweenPriceRange(double low, double high) {
        List<Product> products = productService.getByPriceRange(low, high);
        return null;
    }

    @GetMapping("products/name")
    public String getProductByName(String name) {
        Product product = productService.getByName(name);
        return null;
    }

    @GetMapping("products/category")
    public String getProductsByCategory(String category) {
        List<Product> products = productService.getByCategory(category);
        return null;
    }

    @GetMapping("{customerId}/orders")
    public String getCustomerOrders(@PathVariable Long customerId) {
        List<Order> orders = orderService.getByCustomerId(customerId);
        return null;
    }

    @GetMapping("{customerId}/finalOrder")
    public String showFinalOrder(@PathVariable Long customerId) {
        Cart customerCart = customerService.get(customerId).getCart();
        Order finalOrder = cartService.showFinalOrder(customerCart.getId());
        return null;
    }

    @PostMapping("{customerId}/finalOrder")
    public String submitFinalOrder(@PathVariable Long customerId) {
        Cart customerCart = customerService.get(customerId).getCart();
        Order submittedOrder = cartService.submitFinalOrder(customerCart.getId());
        return null;
    }

    @GetMapping("{customerId}/cart")
    public String showCustomerCart(@PathVariable Long customerId) {
        Cart customerCart = customerService.get(customerId).getCart();
        return null;
    }

    @PostMapping("{customerId}/cart")
    public String addItemToCart(@PathVariable Long customerId, @RequestBody CartItem item) {
        Cart customerCart = customerService.get(customerId).getCart();
        cartService.addItem(customerCart.getId(), item);
        return null;
    }

    @PostMapping("{customerId}/cart/{itemId}")
    public String removeItemFromCart(@PathVariable Long customerId, @PathVariable Long itemId) {
        Cart customerCart = customerService.get(customerId).getCart();
        cartService.removeItem(customerCart.getId(), itemId);
        return null;
    }

    @PutMapping("{customerId}/cart/clear")
    public String clearCustomerCart(@PathVariable Long customerId) {
        Cart customerCart = customerService.get(customerId).getCart();
        cartService.clearCart(customerCart.getId());
        return null;
    }
}
