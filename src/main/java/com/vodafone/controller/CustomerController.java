package com.vodafone.controller;

import com.vodafone.model.*;
import com.vodafone.model.dto.CreateUser;
import com.vodafone.service.*;
import com.vodafone.validators.UserAuthorizer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Controller
@AllArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;
    private OrderService orderService;
    private ProductService productService;
    private CartService cartService;
    private SendEmailService sendEmailService;
    private UserAuthorizer userAuthorizer;

    // Home
    @GetMapping("home.htm")
    public String home(HttpSession session, Model model, @RequestParam(required = false) String category) {
        if(userAuthorizer.isActivatedCustomer(session)) {
            List<Product> products;
            if (category != null)
                products = this.productService.getByCategory(category);
            else
                products = this.productService.getAll();
            model.addAttribute("products", products);
            return "/customer/shared/home";
        }
        else{
            return "redirect:/login.htm";
        }
    }

    @GetMapping("/search/home.htm")
    public String search(HttpSession session,Model model, @RequestParam(required = false) String category,
                         @RequestParam(required = false) String name) {
        if(userAuthorizer.isActivatedCustomer(session)) {
            List<Product> products = new ArrayList<>(this.productService.getByCategory(category));
            Product selectedProduct = this.productService.getByName(name);
            if (selectedProduct != null)
                products.add(selectedProduct);
            model.addAttribute("products", products);
            return "/customer/shared/home";
        }
        else {
            return "redirect:/login.htm";
        }
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
    public String resetPasswordLoader(HttpSession session,Model model) {
        if(userAuthorizer.customerExists(session)) {
            model.addAttribute("resetUser", new CreateUser());
            return "resetPassword";
        }
        else{
            return "redirect:/login.htm";
        }
    }

    @PostMapping("reset.htm")
    public String resetPassword(@Valid @ModelAttribute("resetUser") CreateUser user,
                                BindingResult bindingResult, HttpSession session) {
        if(userAuthorizer.customerExists(session)) {
            if (bindingResult.hasErrors()) {
                Map<String, Object> modelBind = bindingResult.getModel();
                System.out.println(modelBind);
                return "registration";
            }
            System.out.println(user);
            if (customerService.resetPassword(session.getAttribute("email").toString(), user.getPassword()))
                return "login";
            return "resetPassword";
        }
        else{
            return "redirect:/login.htm";
        }
    }


    @GetMapping("/products/{id}/details.htm")
    public String viewProductDetails(HttpSession session,Model model, @PathVariable Long id) {
        if(userAuthorizer.isActivatedCustomer(session)) {
            Product product = this.productService.get(id);
            model.addAttribute("product", product);
            return "/customer/product/detail";
        }
        else{
            return "redirect:/login.htm";
        }
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

    //edit in frontend
    @GetMapping("/orders.htm")
    public String getCustomerOrders(HttpSession session, Model model) {
        if(userAuthorizer.isActivatedCustomer(session)) {
            Long customerId = (long) session.getAttribute("id");
            List<Order> orders = orderService.getByCustomerId(customerId);
            model.addAttribute("orders", orders);
            return "/customer/shared/orders";
        }
        else{
            return "redirect:/login.htm";
        }
    }
    @PostMapping("/submitOrder.htm")
    @ResponseBody
    public String submitFinalOrder(HttpSession session) {
        if(userAuthorizer.isActivatedCustomer(session)) {
            Long customerId = (long) session.getAttribute("id");
            Cart customerCart = customerService.get(customerId).getCart();
            Order submittedOrder = cartService.submitFinalOrder(customerCart.getId());
            boolean created = orderService.create(submittedOrder);
            if (created)
                return "true";
            return "false";
        }
        else{
            return "redirect:/login.htm";
        }
    }

    @GetMapping("showCart.htm")
    public String showCustomerCart(Model model, HttpSession session) {
        if(userAuthorizer.isActivatedCustomer(session)) {
            Long customerId = (long) session.getAttribute("id");
            Cart customerCart = customerService.get(customerId).getCart();
            List<CartItem> cartItems = customerCart.getItems();
            double totalCartPrice = cartItems.stream().mapToDouble(CartItem::getTotal).sum();
            model.addAttribute("items", cartItems);
            model.addAttribute("orderTotal", totalCartPrice);

            return "/customer/shared/cart";
        }
        else{
            return "redirect:/login.htm";
        }
    }

    //change in front end
    @PostMapping("/addToCart")
    @ResponseBody
    public String addItemToCart(HttpSession session, @RequestParam Long itemId,
                                @RequestParam int quantity) {
        if(userAuthorizer.isActivatedCustomer(session)) {
            Long customerId = (long) session.getAttribute("id");
            Cart customerCart = customerService.get(customerId).getCart();
            Product product = productService.get(itemId);

            CartItem cartItem = new CartItem(quantity, product, customerCart);
            boolean added = cartService.addItem(customerCart.getId(), cartItem);
            if (added)
                return "true";
            return "false";
        }
        else{
            return "redirect:/login.htm";
        }
    }

    //change in front end
    @DeleteMapping("showCart.htm")
    @ResponseBody
    public String removeItemFromCart(HttpSession session, @RequestParam Long itemId) {
        if(userAuthorizer.isActivatedCustomer(session)) {
            Long customerId = (long) session.getAttribute("id");
            Cart customerCart = customerService.get(customerId).getCart();
            boolean deleted = cartService.removeItem(customerCart.getId(), itemId);
            if (deleted)
                return "true";
            return "false";
        }
        else{
            return "redirect:/login.htm";
        }
    }


    @GetMapping("registration.htm")
    public String registration(Model model) {
            model.addAttribute("customerDTO", new Customer());
            return "registration";

    }

    //todo: change customer param type to CreateUser DTO
    @PostMapping("registration.htm")
    public String register(@Valid @ModelAttribute("customerDTO") Customer customerDTO, BindingResult bindingResult,
                           HttpServletRequest request, HttpSession session) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> modelBind = bindingResult.getModel();
            System.out.println(modelBind);
            return "registration";
        }
        String otp = sendEmailService.getRandom();
        customerDTO.setCode(otp);
        //set session attributes
        session.setAttribute("email", customerDTO.getEmail());
        session.setAttribute("username", customerDTO.getUserName());
        session.setAttribute("verificationCode", otp);
        customerService.create(customerDTO);
        if (sendEmailService.sendEmail(customerDTO, EmailType.ACTIVATION, session)) {

            return "redirect:/customer/verify.htm";
        } else {
            return "registration";
        }

    }

    @GetMapping("resend.htm")
    public String resendOtp(HttpSession session) {
        if(userAuthorizer.customerExists(session)) {
            Customer selectedCustomer = this.customerService.getByUserName(session.getAttribute("username").toString());
            String otp = this.sendEmailService.getRandom();
            selectedCustomer.setCode(otp);
            session.setAttribute("verificationCode", otp);
            this.customerService.update(selectedCustomer.getId(), selectedCustomer);
            if (sendEmailService.sendEmail(selectedCustomer, EmailType.ACTIVATION, session)) {

                return "redirect:/customer/verify.htm";
            } else {
                return "registration";
            }
        }
        else{
            return "redirect:/login.htm";
        }
    }

    @GetMapping("/verify.htm")
    public String verify(Model model, HttpSession session) {
        if(userAuthorizer.customerExists(session)) {
            model.addAttribute("customer", new Customer());

            final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
            executor.schedule(() -> {
                customerService.expireOtp(session.getAttribute("username").toString());
            }, 1, TimeUnit.MINUTES);

            return "verify";
        }
        else{
            return "redirect:/login.htm";
        }
    }


    @PostMapping("verify.htm")
    public String verifyCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult, HttpSession session) {
        if(userAuthorizer.customerExists(session)) {
            if (bindingResult.hasErrors()) {
                Map<String, Object> modelBind = bindingResult.getModel();
                System.out.println(modelBind);
                return "verify";
            }
            Customer customer1 = customerService.getByMail((String) session.getAttribute("email"));
            if (customer1 == null) {
                //todo: display email not found error
                return "404";
            } else {
                if (customer1.getCode().equals(customer.getCode())) {
                    System.out.println(customer1.getEmail());
                    System.out.println("updated " + customerService.updateStatusActivated(customer1.getEmail()));
                    customerService.updateStatusActivated(customer1.getEmail());
                    return "redirect:/customer/home.htm";
                } else {
                    return "404";
                }
            }
        }
        else{
            return "redirect:/login.htm";
        }
    }


    @PutMapping("/increment")
    @ResponseBody
    public String incrementProductQuantity(HttpSession session, @RequestParam Long productId) {
        if(userAuthorizer.isActivatedCustomer(session)) {
            //retrieve cartId from session
            Long customerId = (long) session.getAttribute("id");
            Long cartId = customerService.get(customerId).getCart().getId();
            int newQuantity = cartService.incrementProductQuantity(cartId, productId, 1);
            if (newQuantity > 0)
                return "true";
            return "false";
        }
        else{
            return "redirect:/login.htm";
        }
    }

    @PutMapping("/decrement")
    @ResponseBody
    public String decrementProductQuantity(HttpSession session, @RequestParam Long productId) {
        if(userAuthorizer.isActivatedCustomer(session)) {
            Long customerId = (long) session.getAttribute("id");
            Long cartId = customerService.get(customerId).getCart().getId();
            int newQuantity = cartService.decrementProductQuantity(cartId, productId);
            if (newQuantity >= 0)
                return "true";
            return "false";
        }
        else{
            return "redirect:/login.htm";
        }
    }
}
