package com.vodafone.controller;

import com.vodafone.exception.product.GetProductException;
import com.vodafone.model.*;
import com.vodafone.model.dto.ResetPasswordDTO;
import com.vodafone.service.*;
import com.vodafone.validators.CustomerValidator;
import com.vodafone.validators.UserAuthorizer;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private CustomerValidator customerValidator;
    private HashService hashService;

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);


    // Home
    @GetMapping("home.htm")
    public String home(HttpSession session, Model model, @RequestParam(required = false) String category) {
        if (userAuthorizer.isActivatedCustomer(session)) {
            List<Product> products = new ArrayList<>();
            if (category != null) {
                try {
                    products.addAll(this.productService.getByCategory(category));
                } catch (GetProductException e) {
                    logger.warn(e.getMessage());
                }
            } else
                products.addAll(this.productService.getAvailableProducts());
            model.addAttribute("products", products);
            return "/customer/shared/home";
        } else {
            return "redirect:/login.htm";
        }
    }

    @GetMapping("/search/home.htm")
    public String search(HttpSession session, Model model, @RequestParam(required = false) String category,
                         @RequestParam(required = false) String name) {
        if (userAuthorizer.isActivatedCustomer(session)) {
            List<Product> products = new ArrayList<>();
            Product selectedProduct;

            try {
                products.addAll(this.productService.getByCategory(category));
            } catch (GetProductException e) {
                logger.warn(e.getMessage());
            }

            try {
                selectedProduct = this.productService.getByName(name);
                products.add(selectedProduct);
            } catch (GetProductException e) {
                logger.info(e.getMessage());
            }
            model.addAttribute("products", products);
            return "/customer/shared/home";
        } else {
            return "redirect:/login.htm";
        }
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @GetMapping("reset.htm")
    public String resetPasswordLoader(HttpSession session, Model model) {
        if (userAuthorizer.customerExists(session)) {
            model.addAttribute("resetUser", new ResetPasswordDTO());
            return "resetPassword";
        } else {
            return "redirect:/login.htm";
        }
    }

    @PostMapping("reset.htm")
    public String resetPassword(@Valid @ModelAttribute("resetUser") ResetPasswordDTO user,
                                BindingResult bindingResult, HttpSession session) {
        if (userAuthorizer.customerExists(session)) {
            if (bindingResult.hasErrors()) {
                Map<String, Object> modelBind = bindingResult.getModel();
                System.out.println(modelBind);
                return "registration";
            }
            System.out.println(user);
            if (customerService.resetPassword(session.getAttribute("email").toString(), hashService.encryptPassword(user.getPassword(), session.getAttribute("email").toString())))
                return "redirect:/login.htm";
            return "resetPassword";
        } else {
            return "redirect:/login.htm";
        }
    }


    @GetMapping("/products/{id}/details.htm")
    public String viewProductDetails(HttpSession session, Model model, @PathVariable Long id) {
        if (userAuthorizer.isActivatedCustomer(session)) {
            Product product;
            try {
                product = this.productService.getById(id);
                model.addAttribute("product", product);
            } catch (GetProductException e) {
                logger.warn(e.getMessage());
                // return custom 404 error page
            }
            return "/customer/product/detail";
        } else {
            return "redirect:/login.htm";
        }
    }

    @GetMapping("/orders.htm")
    public String getCustomerOrders(HttpSession session, Model model) {
        if (userAuthorizer.isActivatedCustomer(session)) {
            Long customerId = (long) session.getAttribute("id");
            List<Order> orders = orderService.getByCustomerId(customerId);
            model.addAttribute("orders", orders);
            return "/customer/shared/orders";
        } else {
            return "redirect:/login.htm";
        }
    }

    @PostMapping("/submitOrder.htm")
    @ResponseBody
    public String submitFinalOrder(HttpSession session) {
        if (userAuthorizer.isActivatedCustomer(session)) {
            Long customerId = (long) session.getAttribute("id");
            Cart customerCart = customerService.getById(customerId).getCart();
            Order submittedOrder = cartService.submitFinalOrder(customerCart.getId());
            boolean created = orderService.create(submittedOrder);
            if (created)
                return "200";
            return "500";
        } else {
            return "401";
        }
    }

    @GetMapping("showCart.htm")
    public String showCustomerCart(Model model, HttpSession session) {
        if (userAuthorizer.isActivatedCustomer(session)) {
            Long customerId = (long) session.getAttribute("id");
            Cart customerCart = customerService.getById(customerId).getCart();
            List<CartItem> cartItems = customerCart.getItems();
            double totalCartPrice = cartItems.stream().mapToDouble(CartItem::getTotal).sum();
            model.addAttribute("items", cartItems);
            model.addAttribute("orderTotal", totalCartPrice);

            return "/customer/shared/cart";
        } else {
            return "redirect:/login.htm";
        }
    }

    //change in front end
    @PostMapping("/addToCart")
    @ResponseBody
    public String addItemToCart(HttpSession session, @RequestParam Long itemId,
                                @RequestParam int quantity) {
        if (userAuthorizer.isActivatedCustomer(session)) {
            Long customerId = (long) session.getAttribute("id");
            Cart customerCart = customerService.getById(customerId).getCart();
            Product product = null;
            try {
                product = productService.getById(itemId);
            } catch (GetProductException e) {
                logger.warn(e.getMessage());
            }

            CartItem cartItem = new CartItem(quantity, product, customerCart);
            int newQuantity = cartService.addItem(customerCart.getId(), cartItem);
            if (newQuantity == 0)
                return "409";  //conflict
            if (newQuantity == -1)
                return "500";
            return "200";
        } else {
            return "401";
        }
    }

    //change in front end
    @DeleteMapping("showCart.htm")
    @ResponseBody
    public String removeItemFromCart(HttpSession session, @RequestParam Long itemId) {
        if (userAuthorizer.isActivatedCustomer(session)) {
            Long customerId = (long) session.getAttribute("id");
            Cart customerCart = customerService.getById(customerId).getCart();
            boolean deleted = cartService.removeItem(customerCart.getId(), itemId);
            if (deleted)
                return "200";
            return "500";
        } else {
            return "401";
        }
    }


    @GetMapping("registration.htm")
    public String registration(Model model) {
        model.addAttribute("customerDTO", new Customer());
        return "registration";

    }

    @PostMapping("registration.htm")
    public String register(@Valid @ModelAttribute("customerDTO") Customer customerDTO, BindingResult bindingResult,
                           HttpSession session) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> modelBind = bindingResult.getModel();
            System.out.println(modelBind);
            return "registration";
        }
        customerValidator.validate(customerDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            Map<String, Object> modelBind = bindingResult.getModel();
            System.out.println(modelBind);
            return "registration";
        }
        System.out.println("hola");
        String otp = sendEmailService.getRandom();
        customerDTO.setCode(otp);
        //set session attributes
        session.setAttribute("email", customerDTO.getEmail());
        session.setAttribute("username", customerDTO.getUserName());
        session.setAttribute("verificationCode", otp);
        customerDTO.setPassword(customerDTO.getPassword());
        customerService.create(customerDTO);
        if (sendEmailService.sendEmail(customerDTO, EmailType.ACTIVATION, session)) {
            return "redirect:/customer/verify.htm";
        } else {
            return "registration";
        }

    }

    @GetMapping("resend.htm")
    public String resendOtp(HttpSession session) {
        if (userAuthorizer.customerExists(session)) {
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
        } else {
            return "redirect:/login.htm";
        }
    }

    @GetMapping("/verify.htm")
    public String verify(Model model, HttpSession session) {
        if (userAuthorizer.customerExists(session)) {
            model.addAttribute("customer", new Customer());

            final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
            executor.schedule(() -> {
                customerService.expireOtp(session.getAttribute("username").toString());
            }, 1, TimeUnit.MINUTES);

            return "verify";
        } else {
            return "redirect:/login.htm";
        }
    }


    @PostMapping("verify.htm")
    public String verifyCustomer(@Valid @NotNull @NotBlank @RequestParam("verificationCode") String verificationCode, HttpSession session, Model model) {
        if (userAuthorizer.customerExists(session)) {
            Customer selectedCustomer = customerService.getByMail((String) session.getAttribute("email"));
            if (selectedCustomer == null) {
                return "registration";
            }
            if (selectedCustomer.getCode() == null) {
                model.addAttribute("error", "OTP has been expired");
                return "verify";
            }
            if (selectedCustomer.getCode().equals(verificationCode)) {
                System.out.println(selectedCustomer.getEmail());
                System.out.println("updated " + customerService.updateStatusActivated(selectedCustomer.getEmail()));
                customerService.updateStatusActivated(selectedCustomer.getEmail());
                return "redirect:/customer/home.htm";
            } else {
                model.addAttribute("error", "OTP is invalid");
                return "verify";
            }
        } else {
            return "redirect:/login.htm";
        }
    }

    @PutMapping("/increment")
    @ResponseBody
    public String incrementProductQuantity(HttpSession session, @RequestParam Long productId) {
        if (userAuthorizer.isActivatedCustomer(session)) {
            //retrieve cartId from session
            Long customerId = (long) session.getAttribute("id");
            Long cartId = customerService.getById(customerId).getCart().getId();
            int newQuantity = cartService.incrementProductQuantity(cartId, productId, 1);
            if (newQuantity == 0)
                return "409";  //conflict
            if (newQuantity == -1)
                return "500";
            return "200";
        } else {
            return "401";  //unauthorized
        }
    }

    @PutMapping("/decrement")
    @ResponseBody
    public String decrementProductQuantity(HttpSession session, @RequestParam Long productId) {
        if (userAuthorizer.isActivatedCustomer(session)) {
            Long customerId = (long) session.getAttribute("id");
            Long cartId = customerService.getById(customerId).getCart().getId();
            int newQuantity = cartService.decrementProductQuantity(cartId, productId,1);
            if (newQuantity == -1)
                return "500";
            return "200";
        } else {
            return "401";
        }
    }
}
