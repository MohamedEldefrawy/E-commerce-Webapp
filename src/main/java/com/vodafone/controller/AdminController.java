package com.vodafone.controller;

import com.vodafone.model.Admin;
import com.vodafone.model.Product;
import com.vodafone.model.Role;
import com.vodafone.model.dto.CreateAdmin;
import com.vodafone.model.dto.CreateProduct;
import com.vodafone.model.dto.CreateUser;
import com.vodafone.model.dto.SetAdminPassword;
import com.vodafone.service.AdminService;
import com.vodafone.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admins")

public class AdminController {
    AdminService adminService;
    ProductService productService;

    public AdminController(AdminService adminService, ProductService productService) {
        this.adminService = adminService;
        this.productService = productService;
        //todo: save super admin config in config file as a bean
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
        return "admin/viewAllAdmins";

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
    @ResponseBody
    public String delete(@RequestParam(required = false) Long id) {
        boolean deleted = adminService.delete(id);
//        System.out.println(deleted);
//        return "redirect:/admins/admins.htm";
        if (deleted)
            return "true";
        return "false";

    }

    /*@PutMapping("/admins.htm")
    public boolean create(Admin admin) {
        return adminService.create(admin);
    }*/

    @GetMapping("/updateAdmin.htm")
    public String updateAdmin(Model model, @RequestParam Long id) {
        Admin admin = adminService.get(id);
        model.addAttribute("admin", admin);
        return "admin/updateAdmin";
    }

    @GetMapping("/createAdmin.htm")
    public String getCreateAdminPage(Model model) {
        model.addAttribute("admin", new CreateAdmin());
        return "admin/createAdmin";

    }

    @PostMapping("/createAdmin.htm")
    public String create(@Valid @ModelAttribute("admin") CreateAdmin createAdmin, BindingResult bindingResult) {
        Map<String, Object> model = bindingResult.getModel();
        if (bindingResult.hasErrors()) {
            System.out.println(model);
            return "admin/createAdmin";
        }
        Admin admin = new Admin();
        admin.setUserName(createAdmin.getUserName());
        admin.setRole(Role.Admin);
        admin.setEmail(createAdmin.getEmail());
        //admin.setPassword("123456");
        admin.setFirstLogin(true);
        if (adminService.create(admin))
            return "redirect:/admins/admins.htm";
        else {
            //model.put("errors","Duplicate");
            return "admin/createAdmin";
        }
    }

    // Home
    @GetMapping("home.htm")
    public String home(Model model) {
        List<Product> productList = this.productService.getAll();
        model.addAttribute("products", productList);
        return "shared/home";
    }

    //    Products End Points
    @GetMapping("/products/show.htm")
    public String showAllProducts(Model model) {
        model.addAttribute("products", this.productService.getAll());
        model.addAttribute("id", 0L);
        return "products/products";
    }

    @GetMapping("/products/update.htm")
    public String updateProduct(Model model, @RequestParam Long id) {
        Product selectedProduct = this.productService.get(id);
        model.addAttribute("product", selectedProduct);
        return "products/update";
    }


    @PostMapping("/products/update.htm")
    public String submit(@Valid @ModelAttribute("product") CreateProduct product,
                         BindingResult bindingResult,
                         @RequestParam("image") CommonsMultipartFile image,
                         HttpSession session,
                         @RequestParam Long id) {
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
    }

    @GetMapping("/products/create.htm")
    public String createProduct(Model model) {
        model.addAttribute("product", new CreateProduct());
        return "products/create";
    }

    @PostMapping("/products/create.htm")
    public String save(@Valid @ModelAttribute("product") CreateProduct product,
                       BindingResult bindingResult,
                       @RequestParam("image") CommonsMultipartFile image,
                       HttpSession session) {
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
    }


    @DeleteMapping("/products/show.htm")
    @ResponseBody
    public String deleteProduct(@RequestParam(required = false) Long id) {
        boolean result = this.productService.delete(id);
        if (result)
            return "true";
        return "false";
    }

    @PutMapping("/{id}/resetPassword.htm")
    public String updatePassword(@PathVariable Long id, @Valid @ModelAttribute("password") String newPassword, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> model = bindingResult.getModel();
            System.out.println(model);
            //if input has error forward to same page
            return "/{id}/resetPassword.htm";
        }
        if (adminService.updatePassword(id, newPassword)) {
            //todo forward to admin home page
            return null;
        }
        //todo forward to admin home page
        return null;
    }

    @PostMapping("/updateAdmin.htm")
    public String submit(@Valid @ModelAttribute("admin") CreateAdmin admin, BindingResult bindingResult,
                         @RequestParam Long id) {
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
    }

    @GetMapping
    public String setAdminPasswordLoader(Model model) {
        model.addAttribute("resetAdmin", new SetAdminPassword());
        return "setAdminPassword";
    }

    @PostMapping
    public String setAdminPassword(@Valid @ModelAttribute("resetAdmin") SetAdminPassword admin, BindingResult bindingResult,
                                   HttpSession session) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> modelBind = bindingResult.getModel();
            System.out.println(modelBind);
            return "login";
        }
        String email = session.getAttribute("email").toString();
        Admin existingAdmin = adminService.getByEmail(email);
        //get user's email from session
        if (!Objects.equals(admin.getOldPassword(), existingAdmin.getPassword())) {
            System.out.println("Non-matching passwords!!");
            return "login";
            //todo: throw and catch exception
        }
        String newPassword = admin.getNewPassword();
        existingAdmin.setPassword(newPassword);
        adminService.update(existingAdmin.getId(), existingAdmin);
        return "setAdminPassword";
    }
}
