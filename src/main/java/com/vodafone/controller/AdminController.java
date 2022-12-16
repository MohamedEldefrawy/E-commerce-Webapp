package com.vodafone.controller;

import com.vodafone.exception.admin.CreateAdminException;
import com.vodafone.exception.admin.GetAdminException;
import com.vodafone.exception.product.CreateProductException;
import com.vodafone.exception.product.GetProductException;
import com.vodafone.model.Admin;
import com.vodafone.model.EmailType;
import com.vodafone.model.Product;
import com.vodafone.model.Role;
import com.vodafone.model.dto.CreateAdmin;
import com.vodafone.model.dto.CreateProduct;
import com.vodafone.model.dto.UpdateProductDto;
import com.vodafone.service.AdminService;
import com.vodafone.service.HashService;
import com.vodafone.service.ProductService;
import com.vodafone.service.SendEmailService;
import com.vodafone.validators.AdminValidator;
import com.vodafone.validators.UserAuthorizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admins")

public class AdminController {
    private final AdminService adminService;
    private final ProductService productService;
    private final UserAuthorizer userAuthorizer;
    private final AdminValidator validator;

    private final HashService hashService;

    private final SendEmailService emailService;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public AdminController(AdminService adminService, ProductService productService,
                           AdminValidator adminValidator, UserAuthorizer userAuthorizer, HashService hashService, SendEmailService emailService) {
        this.adminService = adminService;
        this.productService = productService;
        this.validator = adminValidator;
        this.userAuthorizer = userAuthorizer;
        this.hashService = hashService;
        this.emailService = emailService;
    }


    @GetMapping("/admins.htm")
    public String getAll(HttpSession session, Model model) {
        List<Admin> adminList = new ArrayList<>();
        if (userAuthorizer.authorizeAdmin(session)) {
            try {
                adminList.addAll(this.adminService.getAll());
            } catch (GetAdminException e) {
                logger.warn(e.getMessage());
                //todo: redirect to 404
            }
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
            Long sessionId = (Long) session.getAttribute("id");
            if (id != 2 && !Objects.equals(sessionId, id)) {
                boolean deleted = false;
                try {
                    deleted = adminService.deleteAdmin(id);
                }
                catch (GetAdminException e){
                    logger.warn(e.getMessage());
                    //todo: redirect to 404
                }
                if (deleted)
                    return "200"; //deleted successfully
                return "500"; //server encountered error while processing request
            }
            return "405"; //Not allowed
        } else {
            return "401";  //unauthorized
        }
    }

    @GetMapping("/updateAdmin.htm")
    public String updateAdminPage(HttpSession session, Model model, @RequestParam Long id) {
        if (userAuthorizer.authorizeAdmin(session)) {
            Admin admin;
            try {
                admin = adminService.getAdminById(id);
            }
            catch (GetAdminException e){
                logger.warn(e.getMessage());
                return "admin/viewAllAdmins"; //todo: redirect to 404?
            }
            int idInt = id.intValue();
            CreateAdmin createAdmin = new CreateAdmin(idInt, admin.getUserName(), admin.getEmail());
            model.addAttribute("admin", createAdmin);
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
            boolean created = false;
            try{
                created = adminService.createAdmin(admin);
            }
            catch (CreateAdminException e){
                logger.warn(e.getMessage());
            }
            if (created) {
                session.setAttribute("dec_password", admin.getPassword());
                session.setAttribute("newAdminEmail", admin.getEmail());
                //encrypt admin password in db
                String encrypted = hashService.encryptPassword(admin.getPassword(), admin.getEmail());
                admin.setPassword(encrypted);
                adminService.updatePassword(admin.getId(), encrypted);
                //send email
                emailService.sendEmail(admin, EmailType.SET_ADMIN_PASSWORD, session);
                //redirect to set password
                return "redirect:/admins/admins.htm";
            } else {
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
            List<Product> productList = this.productService.getAvailableProducts();
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
            model.addAttribute("products", this.productService.getAvailableProducts());
            model.addAttribute("id", 0L);
            return "products/products";
        } else {
            return "redirect:/login.htm";
        }
    }

    @GetMapping("/products/update.htm")
    public String updateProduct(HttpSession session, Model model, @RequestParam Long id) {
        if (userAuthorizer.authorizeAdmin(session)) {
            Product selectedProduct = null;
            try {
                selectedProduct = this.productService.getById(id);
            } catch (GetProductException e) {
                logger.warn(e.getMessage());
            }
            model.addAttribute("product", selectedProduct);
            return "products/update";
        } else {
            return "redirect:/login.htm";
        }
    }


    @PostMapping("/products/update.htm")
    public String submit(@Valid @ModelAttribute("product") UpdateProductDto product,
                         BindingResult bindingResult,
                         HttpSession session,
                         @RequestParam Long id) {
        if (userAuthorizer.authorizeAdmin(session)) {

            if (bindingResult.hasErrors()) {
                System.out.println(bindingResult.getModel());
                return "products/update";
            }
            byte[] imageData = product.getImage().getBytes();
            System.out.println(product.getImage().getOriginalFilename());
            String path = session.getServletContext().getRealPath("/") + "resources/static/images/" + product.getImage().getOriginalFilename();
            try {
                if (imageData.length > 0) {
                    FileOutputStream fileOutputStream = new FileOutputStream(path);
                    fileOutputStream.write(imageData);
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }

            Product updatedProduct;
            boolean result = false;
            try {
                updatedProduct = this.productService.getById(product.getId());
                updatedProduct.setDescription(product.getDescription());
                updatedProduct.setCategory(product.getCategory());
                if (imageData.length > 0)
                    updatedProduct.setImage(product.getImage().getOriginalFilename());
                updatedProduct.setPrice(product.getPrice());
                updatedProduct.setName(product.getName());
                updatedProduct.setInStock(product.getInStock());
                result = this.productService.update(id, updatedProduct);

            } catch (GetProductException e) {
                logger.warn(e.getMessage());
            }
            if (result)
                return "redirect:/admins/products/show.htm";
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
                return "products/create";
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
            newProduct.setDeleted(false);
            try {
                this.productService.create(newProduct);
            } catch (CreateProductException e) {
                logger.warn(e.getMessage());
            }
            return "redirect:/admins/products/show.htm";
        } else {
            return "redirect:/login.htm";
        }
    }


    @DeleteMapping("/products/show.htm")
    @ResponseBody
    public String deleteProduct(HttpSession session, @RequestParam(required = false) Long id) {
        if (userAuthorizer.authorizeAdmin(session)) {
            boolean result;
            try {
                result = this.productService.delete(id);
            } catch (GetProductException e) {
                logger.warn(e.getMessage());
                result = false;
            }

            if (result)
                return "200";  //ok
            return "500"; //server error

        } else {
            return "401"; //unauthorized
        }
    }

    @PostMapping("/updateAdmin.htm")
    public String updateAdmin(@Validated @ModelAttribute("admin") CreateAdmin admin, HttpSession session,
                              BindingResult bindingResult,
                              @RequestParam Long id) {
        if (userAuthorizer.authorizeAdmin(session)) {
            if (bindingResult.hasErrors()) {
                return "admin/updateAdmin";
            }
            validator.validate(admin, bindingResult);
            if (bindingResult.hasErrors()) {
                return "admin/updateAdmin";
            }

            Admin updatedAdmin = new Admin();
            updatedAdmin.setEmail(admin.getEmail());
            updatedAdmin.setUserName(admin.getUserName());
            boolean result = false;
            try {
                result = adminService.updateAdmin(id, updatedAdmin);

            }catch (GetAdminException e){
                logger.warn(e.getMessage());
                //todo: redirect to 404
            }
            if (result)
                return "redirect:/admins/admins.htm";
            return "admin/updateAdmin";
        } else {
            return "redirect:/login.htm";
        }
    }

    @GetMapping("setAdminPassword.htm")
    public String setAdminPassword() {
        return "admin/setAdminPassword";
    }

    @PostMapping("setAdminPassword.htm")
    public String setAdminPassword(@Valid @NotNull @NotBlank @RequestParam("newPassword") String newPassword, HttpSession session) {
        String email = session.getAttribute("email").toString();
        Admin admin = null;
        try {
            admin = adminService.getAdminByEmail(email);
        }
        catch (GetAdminException e){
            logger.warn(e.getMessage());
            return "admin/setAdminPassword";
        }
        newPassword = hashService.encryptPassword(newPassword, email);
        admin.setPassword(newPassword);
        try {
            adminService.updatePassword(admin.getId(), newPassword);
            return "redirect:/admins/home.htm";
        }catch (GetAdminException e){
            logger.warn(e.getMessage());
            return "admin/setAdminPassword";
        }
    }
}
