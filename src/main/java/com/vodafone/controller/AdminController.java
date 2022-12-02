package com.vodafone.controller;

import com.vodafone.model.Admin;
import com.vodafone.model.Product;
import com.vodafone.model.Role;

import com.vodafone.model.User;
import com.vodafone.model.dto.CreateAdmin;
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
@RequestMapping("/admins")

public class AdminController {
    AdminService adminService;
    ProductService productService;

    public AdminController(AdminService adminService, ProductService productService) {
        this.adminService = adminService;
        this.productService = productService;
        //create super admin
        Admin admin = new Admin();
        admin.setEmail("admin@gmail.com");
        admin.setPassword("1234");
        admin.setRole(Role.Admin);
        admin.setUserName("admoona");
        admin.setFirstLogin(false);
        this.adminService.create(admin);
    }


    @GetMapping("/admins.htm")

    public String getAll(Model model) {
        List<Admin> adminList = this.adminService.getAll();
        model.addAttribute("admins", adminList);
        return "viewAllAdmins2";

    }

    @GetMapping("/admins.htm/{id}")
    public Admin get(@PathVariable("id") Long id) {
        return adminService.get(id);
    }

//    @DeleteMapping("/admins.htm")
//    public String delete(@RequestParam("id") Long id) {
//        boolean deleted = adminService.delete(id);
//        return "redirect:/admins/admins.htm";
//
//    }
    @DeleteMapping("/admins.htm")
    public String delete(@RequestParam(required = false) Long id) {
        boolean deleted = adminService.delete(id);
        System.out.println(deleted);
        return "redirect:/admins/admins.htm";

    }

    /*@PutMapping("/admins.htm")
    public boolean create(Admin admin) {
        return adminService.create(admin);
    }*/

    @PutMapping("/admins.htm")
    public boolean update(Long id, Admin admin) {
        return adminService.update(id, admin);
    }

    @GetMapping("/createAdmin.htm")
    public String getCreateAdminPage(Model model) {
        model.addAttribute("admin", new CreateAdmin());
        return "createAdmin";

    }

    @PostMapping("/createAdmin.htm")
    public String addUser(@Valid @ModelAttribute("admin") CreateAdmin createAdmin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> model = bindingResult.getModel();
            System.out.println(model);
            return "createAdmin";
        }
        Admin admin = new Admin();
        admin.setUserName(createAdmin.getUsername());
        admin.setRole(Role.Admin);
        admin.setEmail(createAdmin.getEmail());
        //admin.setPassword("123456");
        admin.setFirstLogin(true);
        adminService.create(admin);
        return "redirect:/admins/admins.htm";
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
    @PutMapping("/{id]/resetPassword.htm")
    public String updatePassword(@PathVariable Long id, @Valid @ModelAttribute("password") String newPassword, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> model = bindingResult.getModel();
            System.out.println(model);
            //if input has error forward to same page
            return "/{id]/resetPassword.htm";
        }
        if(adminService.updatePassword(id, newPassword)){
            //todo forward to admin home page
            return null;
        }
        //todo forward to admin home page
        return null;

    }

}
