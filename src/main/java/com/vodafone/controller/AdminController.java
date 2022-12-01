package com.vodafone.controller;

import com.vodafone.model.Admin;
import com.vodafone.model.Product;
import com.vodafone.model.Role;

import com.vodafone.model.User;
import com.vodafone.service.AdminService;
import com.vodafone.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@NoArgsConstructor
@AllArgsConstructor
@RequestMapping("/admins")

public class AdminController {
    AdminService adminService;
    ProductService productService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
        Admin admin = new Admin();
        admin.setEmail("admin@gmail.com");
        admin.setPassword("1234");
        admin.setRole(Role.Admin);
        admin.setUserName("admoona");
        adminService.create(admin);
    }


    @GetMapping("/admins.htm")

    public String getAll(Model model) {
        List<Admin> adminList = this.adminService.getAll();
        model.addAttribute("admins", adminList);
        return "viewAllAdmins";

    }

    @GetMapping("/admins.htm/{id}")
    public Admin get(@PathVariable("id") Long id) {
        return adminService.get(id);
    }

    @DeleteMapping("/admins.htm/{id}")
    public boolean delete(@RequestParam("id") Long id) {
        return adminService.delete(id);
    }

    /*@PutMapping("/admins.htm")
    public boolean create(Admin admin) {
        return adminService.create(admin);
    }*/

    @PostMapping("/admins.htm")
    public boolean update(Long id, Admin admin) {
        return adminService.update(id, admin);
    }

    @GetMapping("/createAdmin.htm")
    public String getCreateAdminPage(Model model) {
        List<Admin> adminList = this.adminService.getAll();
        model.addAttribute("admins", adminList);
        return "createAdmin";

    }

    @PutMapping("/createAdmin.htm")
    public String addUser(@Valid @ModelAttribute("admin") Admin admin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> model = bindingResult.getModel();
            System.out.println(model);
            return "createAdmin";
        }
        adminService.create(admin);
        return "redirect:/admins.htm";
    }

    @GetMapping("/products")
    public String getAllProducts() {
        List<Product> products = productService.getAll();
        return null;
    }

    @GetMapping("/products/{productId}")
    public String getProductById(@PathVariable Long productId) {
        Product product = productService.get(productId);
        return null;
    }

    @PostMapping("/products")
    public String addProduct(@RequestBody Product product) {
        productService.create(product);
        return null;
    }

    @PutMapping("/products/{productId}")
    public String updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        productService.update(productId, product);
        return null;
    }

    @DeleteMapping("/products/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        productService.delete(productId);
        return null;
    }
}
