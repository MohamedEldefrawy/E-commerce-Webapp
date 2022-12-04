package com.vodafone.controller;

import com.vodafone.model.*;
import com.vodafone.model.dto.CreateUser;
import com.vodafone.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String resetPasswordLoader(Model model) {
        model.addAttribute("resetUser", new CreateUser());
        return "resetPassword";
    }

    @PostMapping("reset.htm")
    public String resetPassword(@Valid @ModelAttribute("resetUser") CreateUser user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> modelBind = bindingResult.getModel();
            System.out.println(modelBind);
            return "registration";
        }
        System.out.println(user);
        if (customerService.resetPassword(user.getEmail(), user.getPassword()))
            return "login";
        return "resetPassword";
    }


    @GetMapping("/products/{id}/details.htm")
    public String viewProductDetails(Model model, @PathVariable Long id) {
        Product product = this.productService.get(id);
        model.addAttribute("product", product);
        return "/customer/product/detail";
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

    @GetMapping("/orders.htm")
    public String getCustomerOrders(Model model, @RequestParam Long customerId) {
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
        boolean created = orderService.create(submittedOrder);
        if (created)
            return "true";
        //todo redirect to error page
        return "false";
    }

    @GetMapping("showCart.htm")
    public String showCustomerCart(Model model, @RequestParam Long customerId) {
        Cart customerCart = customerService.get(customerId).getCart();
        List<CartItem> cartItems = customerCart.getItems();
        double totalCartPrice = cartItems.stream().mapToDouble(CartItem::getTotal).sum();
        model.addAttribute("items", cartItems);
        model.addAttribute("orderTotal", totalCartPrice);

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
    
    @GetMapping("login.htm")
    public String login() {
        return "login";
    }

    @PostMapping("registration.htm")
    public String register(@Valid @ModelAttribute("customerDTO") Customer customerDTO, BindingResult bindingResult,
                           HttpServletRequest request, HttpSession session) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> modelBind = bindingResult.getModel();
            System.out.println(modelBind);
            return "registration";
        }
        System.out.println(customerDTO);
        String otp = sendEmailService.getRandom();
        customerDTO.setCode(otp);
        //todo: check for username and email uniqueness
        if (customerService.getByMail(customerDTO.getEmail()) == null) {
            System.out.println("Email exists");
            return "";
            //todo: display error for not unique email
        }
        if (customerService.getByUserName(customerDTO.getUserName()) == null) {
            System.out.println("Username exists");
            return "";
            //todo: display error for not unique username
        }
        customerService.create(customerDTO);
        if (sendEmailService.sendEmail(customerDTO, EmailType.ACTIVATION, session)) {
//            HttpSession session = request.getSession();
            session.setAttribute("email", customerDTO.getEmail());
            session.setAttribute("password", customerDTO.getPassword());
            session.setAttribute("userName", customerDTO.getUserName());
            session.setAttribute("verificationCode", otp);
            System.out.println(session);
            return "redirect:/customer/verify.htm";
        } else {
            return "registration";
        }

    }

    @GetMapping("/verify.htm")
    public String verify(Model model) {
        //TODO: how to integrate otp part
        return "verify";
    }
}
