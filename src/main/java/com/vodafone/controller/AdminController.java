package com.vodafone.controller;

import com.vodafone.model.Admin;
import com.vodafone.model.Product;
import com.vodafone.model.Role;
import com.vodafone.model.dto.CreateAdmin;
import com.vodafone.model.dto.CreateProduct;
import com.vodafone.service.AdminService;
import com.vodafone.service.HashService;
import com.vodafone.service.ProductService;
import com.vodafone.validators.AdminValidator;
import com.vodafone.validators.UserAuthorizer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admins")

public class AdminController {
    AdminService adminService;
    ProductService productService;
    private UserAuthorizer userAuthorizer;
    private AdminValidator validator;

    private HashService hashService;

    public AdminController(AdminService adminService, ProductService productService,
                           AdminValidator adminValidator, UserAuthorizer userAuthorizer, HashService hashService) {
        this.adminService = adminService;
        this.productService = productService;
        this.validator = adminValidator;
        this.userAuthorizer = userAuthorizer;
        this.hashService = hashService;
        //todo: save super admin config in config file as a bean
        //create super admin
        Admin admin = new Admin();
        admin.setEmail("admin@gmail.com");
        admin.setRole(Role.Admin);
        admin.setUserName("admoona");
        admin.setPassword(hashService.encryptPassword("12345678", admin.getUserName()));
        System.out.println(admin.getPassword());
        admin.setFirstLogin(false);
        this.adminService.create(admin);
    }


    @GetMapping("/admins.htm")
    public String getAll(HttpSession session, Model model) {
        if (userAuthorizer.authorizeAdmin(session)) {
            List<Admin> adminList = this.adminService.getAll();
            model.addAttribute("admins", adminList);
            return "admin/viewAllAdmins";
        } else {
            return "redirect:/login.htm";
        }
    }

    @DeleteMapping("/admins.htm")
    @ResponseBody
    public String delete(HttpSession session, @RequestParam(required = false) Long id) {
        if (userAuthorizer.authorizeAdmin(session)) {
            boolean deleted = adminService.delete(id);
            if (deleted)
                return "true";
            return "false";
        } else {
            return "redirect:/login.htm";
        }
    }

    @GetMapping("/updateAdmin.htm")
    public String updateAdmin(HttpSession session, Model model, @RequestParam Long id) {
        if (userAuthorizer.authorizeAdmin(session)) {
            Admin admin = adminService.get(id);
            model.addAttribute("admin", admin);
            return "admin/updateAdmin";
        } else {
            return "redirect:/login.htm";
        }
    }

    @GetMapping("/createAdmin.htm")
    public String getCreateAdminPage(HttpSession session, Model model) {
        if (userAuthorizer.authorizeAdmin(session)) {
            model.addAttribute("admin", new CreateAdmin());
            return "admin/createAdmin";
        } else {
            return "redirect:/login.htm";
        }
    }

    @PostMapping("/createAdmin.htm")
    public String create(@ModelAttribute("admin") @Validated CreateAdmin createAdmin,
                         HttpSession session, BindingResult bindingResult) {
        if (userAuthorizer.authorizeAdmin(session)) {
            Map<String, Object> model = bindingResult.getModel();
            //check input is correct
            if (bindingResult.hasErrors()) {
                return "admin/createAdmin";
            }
            //validate username and email aren't duplicated
            validator.validate(createAdmin, bindingResult);
            if (bindingResult.hasErrors()) {
                System.out.println(model);
                return "admin/createAdmin";
            }

            Admin admin = new Admin();
            admin.setUserName(createAdmin.getUserName());
            admin.setRole(Role.Admin);
            admin.setEmail(createAdmin.getEmail());
            admin.setFirstLogin(true);
            if (adminService.create(admin))
                return "redirect:/admins/admins.htm";
            else {
                //model.put("errors","Duplicate");
                return "admin/createAdmin";
            }
        } else {
            return "redirect:/login.htm";
        }
    }

    // Home
    @GetMapping("home.htm")
    public String home(HttpSession session, Model model) {
        if (userAuthorizer.authorizeAdmin(session)) {
            List<Product> productList = this.productService.getAll();
            model.addAttribute("products", productList);
            return "shared/home";
        } else {
            return "redirect:/login.htm";
        }
    }

    //    Products End Points
    @GetMapping("/products/show.htm")
    public String showAllProducts(HttpSession session, Model model) {
        if (userAuthorizer.authorizeAdmin(session)) {
            model.addAttribute("products", this.productService.getAll());
            model.addAttribute("id", 0L);
            return "products/products";
        } else {
            return "redirect:/login.htm";
        }
    }

    @GetMapping("/products/update.htm")
    public String updateProduct(HttpSession session, Model model, @RequestParam Long id) {
        if (userAuthorizer.authorizeAdmin(session)) {
            Product selectedProduct = this.productService.get(id);
            model.addAttribute("product", selectedProduct);
            return "products/update";
        } else {
            return "redirect:/login.htm";
        }
    }


    @PostMapping("/products/update.htm")
    public String submit(@Valid @ModelAttribute("product") CreateProduct product,
                         BindingResult bindingResult,
                         @RequestParam("image") CommonsMultipartFile image,
                         HttpSession session,
                         @RequestParam Long id) {
        if (userAuthorizer.authorizeAdmin(session)) {
            if (bindingResult.hasErrors()) {
                return "products/update";
            }
            byte[] imageData = image.getBytes();
            System.out.println(image.getOriginalFilename());
            String path = session.getServletContext().getRealPath("/") + "resources/static/images/" + image.getOriginalFilename();
            try {
                if (imageData.length > 0) {
                    FileOutputStream fileOutputStream = new FileOutputStream(path);
                    fileOutputStream.write(imageData);
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Product updatedProduct = new Product();
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setCategory(product.getCategory());
            updatedProduct.setImage(image.getOriginalFilename());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setName(product.getName());
            updatedProduct.setInStock(product.getInStock());
            boolean result = this.productService.update(id, updatedProduct);
            if (result)
                return "redirect:/product/show.htm";
            return "products/update";
        } else {
            return "redirect:/login.htm";
        }
    }

    @GetMapping("/products/create.htm")
    public String createProduct(HttpSession session, Model model) {
        if (userAuthorizer.authorizeAdmin(session)) {
            model.addAttribute("product", new CreateProduct());
            return "products/create";
        } else {
            return "redirect:/login.htm";
        }
    }

    @PostMapping("/products/create.htm")
    public String save(@Valid @ModelAttribute("product") CreateProduct product,
                       BindingResult bindingResult,
                       @RequestParam("image") CommonsMultipartFile image,
                       HttpSession session) {
        if (userAuthorizer.authorizeAdmin(session)) {
            if (bindingResult.hasErrors()) {
                Map<String, Object> model = bindingResult.getModel();
                return "products/createProduct";
            }
            byte[] imageData = image.getBytes();
            String path = session.getServletContext().getRealPath("/") + "resources/static/images/" + image.getOriginalFilename();
            try {
                if (imageData.length > 0) {
                    FileOutputStream fileOutputStream = new FileOutputStream(path);
                    fileOutputStream.write(imageData);
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            Product newProduct = new Product();
            newProduct.setDescription(product.getDescription());
            newProduct.setCategory(product.getCategory());
            newProduct.setImage(image.getOriginalFilename());
            newProduct.setPrice(product.getPrice());
            newProduct.setName(product.getName());
            newProduct.setInStock(product.getInStock());
            this.productService.create(newProduct);
            return "redirect:/admins/products/show.htm";
        } else {
            return "redirect:/login.htm";
        }
    }


    @DeleteMapping("/products/show.htm")
    @ResponseBody
    public String deleteProduct(HttpSession session, @RequestParam(required = false) Long id) {
        if (userAuthorizer.authorizeAdmin(session)) {
            boolean result = this.productService.delete(id);
            if (result)
                return "true";
            return "false";
        } else {
            return "redirect:/login.htm";
        }
    }

    @PostMapping("/updateAdmin.htm")
    public String submit(@Valid @ModelAttribute("admin") CreateAdmin admin, HttpSession session,
                         BindingResult bindingResult,
                         @RequestParam Long id) {
        if (userAuthorizer.authorizeAdmin(session)) {
            if (bindingResult.hasErrors()) {
                return "admin/updateAdmin";
            }

            Admin updatedAdmin = new Admin();
            updatedAdmin.setEmail(admin.getEmail());
            updatedAdmin.setUserName(admin.getUserName());
            boolean result = adminService.update(id, updatedAdmin);
            if (result)
                return "redirect:/admins/admins.htm";
            return "admin/updateAdmin";
        } else {
            return "redirect:/login.htm";
        }
    }

    @GetMapping("setPassword.htm")
    public String setAdminPassword(Model model, HttpSession session) {
        if (userAuthorizer.authorizeAdmin(session)) {
            return "admin/setAdminPassword";
        } else {
            return "redirect:/login.htm";
        }
    }

    @PostMapping("setPassword.htm")
    public String setAdminPassword(@Valid @NotNull @NotBlank @RequestParam("newPassword") String newPassword, HttpSession session) {
        if (userAuthorizer.authorizeAdmin(session)) {
            String email = session.getAttribute("email").toString();
            Admin admin = adminService.getByEmail(email);
            hashService.encryptPassword(newPassword, (String) session.getAttribute("username"));
            admin.setPassword(newPassword);
            adminService.updatePassword(admin.getId(), newPassword);
            return "redirect:/admins/home.htm";
        } else {
            return "redirect:/login.htm";
        }
    }
}
