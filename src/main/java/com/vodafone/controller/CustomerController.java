package com.vodafone.controller;

import com.vodafone.model.*;
import com.vodafone.model.dto.CreateUser;
import com.vodafone.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;
    private OrderService orderService;
    private ProductService productService;
    private CartService cartService;
    private SendEmailService sendEmailService;

    // Home
    @GetMapping("home.htm")
    public String home(Model model, @RequestParam(required = false) String category) {
        List<Product> products;
        if (category != null)
            products = this.productService.getByCategory(category);
        else
            products = this.productService.getAll();
        model.addAttribute("products", products);
        return "/customer/shared/home";
    }

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

    @GetMapping("showCart.htm")
    public String showCustomerCart(Model model,@RequestParam Long customerId) {
        Cart customerCart = customerService.get(customerId).getCart();
        List<CartItem> cartItems = customerCart.getItems();
        double totalCartPrice = cartItems.stream().mapToDouble(CartItem::getTotal).sum();
        model.addAttribute("items", cartItems);
        model.addAttribute("orderTotal",totalCartPrice);

        return "/customer/shared/cart";
    }

    @PostMapping("/addToCart")
    @ResponseBody
    public String addItemToCart(@RequestParam int customerId, @RequestParam int itemId,
                                @RequestParam int quantity) {

        Cart customerCart = customerService.get(Long.valueOf(customerId)).getCart();
        Product product = productService.get(Long.valueOf(itemId));
        CartItem cartItem = new CartItem(quantity, product, customerCart);
        boolean added = cartService.addItem(customerCart.getId(), cartItem);
        if (added)
            return "true";
        return "false";
    }

    @DeleteMapping("showCart.htm")
    @ResponseBody
    public String removeItemFromCart(@RequestParam Long customerId, @RequestParam Long itemId) {
        Cart customerCart = customerService.get(customerId).getCart();
        boolean deleted = cartService.removeItem(customerCart.getId(), itemId);
        if (deleted)
            return "true";
        return "false";
    }

    @PutMapping("{customerId}/cart/clear")
    public String clearCustomerCart(@PathVariable Long customerId) {
        Cart customerCart = customerService.get(customerId).getCart();
        cartService.clearCart(customerCart.getId());
        return null;
    }


    @GetMapping("registration.htm")
    public String registration(Model model) {
        model.addAttribute("customerDTO", new Customer());
        return "registration";
    }

    @PostMapping("registration.htm")
    public String addCustomer(@Valid @ModelAttribute("customerDTO") Customer customerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object>  modelBind = bindingResult.getModel();
            System.out.println(modelBind);
            return "registration";
        }
        System.out.println(customerDTO);
        String otp = sendEmailService.getRandom();
        customerDTO.setCode(otp);
        customerService.create(customerDTO);
        boolean isEmailSent = sendEmailService.sendEmail(customerDTO);
        if(isEmailSent){
            return "redirect:/customer/verify";
        }
        else {
            return "registration";
        }

    }

    @GetMapping("/verify.htm")
    public String verify(Model model) {
        model.addAttribute("customer", new Customer());
        return "verify";
    }

    @PostMapping("verify.htm")
    public String verifyCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object>  modelBind = bindingResult.getModel();
            System.out.println(modelBind);
            return "verify";
        }
        Customer customer1 = customerService.getByMail(customer.getEmail());
        if(customer1==null){
            return "404";
        } else {
            if(customer1.getCode().equals(customer.getCode())){
                customerService.updateStatusActivated(customer.getEmail());
                return "redirect:/customer/shared/home";
            }else {
                return "404";
            }

        }

    }


}
