package com.vodafone.controller;

import com.vodafone.exception.FileStorageException;
import com.vodafone.exception.admin.CreateAdminException;
import com.vodafone.exception.admin.GetAdminException;
import com.vodafone.exception.product.CreateProductException;
import com.vodafone.exception.product.GetProductException;
import com.vodafone.model.Admin;
import com.vodafone.model.EmailType;
import com.vodafone.model.Product;
import com.vodafone.model.Role;
import com.vodafone.model.dto.CreateAdmin;
import com.vodafone.model.dto.CreateProductDto;
import com.vodafone.model.dto.UpdateProductDto;
import com.vodafone.service.AdminService;
import com.vodafone.service.HashService;
import com.vodafone.service.ProductService;
import com.vodafone.service.SendEmailService;
import com.vodafone.service.file.FileStorageService;
import com.vodafone.util.AdminViews;
import com.vodafone.validators.AdminValidator;
import com.vodafone.validators.UserAuthorizer;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/admins")

public class AdminRestController {
    private final AdminService adminService;
    private final ProductService productService;
    private final UserAuthorizer userAuthorizer;
    private final AdminValidator validator;

    private final HashService hashService;

    private final SendEmailService emailService;

    private final FileStorageService fileStorageService;

    private final Logger logger = LoggerFactory.getLogger(AdminRestController.class);
    private static final String PRODUCT_ATTRIBUTE = "product";

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
            return AdminViews.VIEW_ALL_ADMINS;
        } else {
            return AdminViews.LOGIN_REDIRECT;
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
                } catch (GetAdminException e) {
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
            } catch (GetAdminException e) {
                logger.warn(e.getMessage());
                return AdminViews.VIEW_ALL_ADMINS; //todo: redirect to 404?
            }
            int idInt = id.intValue();
            CreateAdmin createAdmin = new CreateAdmin(idInt, admin.getUserName(), admin.getEmail());
            model.addAttribute("admin", createAdmin);
            return AdminViews.UPDATE_ADMIN;
        } else {
            return AdminViews.LOGIN_REDIRECT;
        }
    }

    @GetMapping("/createAdmin.htm")
    public String getCreateAdminPage(HttpSession session, Model model) {
        if (userAuthorizer.authorizeAdmin(session)) {
            model.addAttribute("admin", new CreateAdmin());
            return AdminViews.CREATE_ADMIN;
        } else {
            return AdminViews.LOGIN_REDIRECT;
        }
    }

    @PostMapping("/createAdmin.htm")
    public String create(@ModelAttribute("admin") @Validated CreateAdmin createAdmin,
                         HttpSession session, BindingResult bindingResult) {
        if (userAuthorizer.authorizeAdmin(session)) {

            //check input is correct
            if (bindingResult.hasErrors()) {
                return AdminViews.CREATE_ADMIN;
            }
            //validate username and email aren't duplicated
            validator.validate(createAdmin, bindingResult);
            if (bindingResult.hasErrors()) {
                return AdminViews.CREATE_ADMIN;
            }

            Admin admin = new Admin();
            admin.setUserName(createAdmin.getUserName());
            admin.setRole(Role.Admin);
            admin.setEmail(createAdmin.getEmail());
            admin.setFirstLogin(true);
            boolean created = false;
            try {
                adminService.createAdmin(admin);
                session.setAttribute("dec_password", admin.getPassword());
                session.setAttribute("newAdminEmail", admin.getEmail());
                //encrypt admin password in db
                String encrypted = hashService.encryptPassword(admin.getPassword(), admin.getEmail());
                admin.setPassword(encrypted);
                adminService.updatePassword(admin.getId(), encrypted); //just set id... impossible to not be set?
                //send email
                emailService.sendEmail(admin, EmailType.SET_ADMIN_PASSWORD, session);
                //redirect to set password
                return AdminViews.ALL_ADMINS_REDIRECT;
            } catch (CreateAdminException e) {
                logger.warn(e.getMessage());
                return AdminViews.CREATE_ADMIN;
            }
        } else {
            return AdminViews.LOGIN_REDIRECT;
        }
    }

    // Home
    @GetMapping("home.htm")
    public String home(HttpSession session) {
        if (userAuthorizer.authorizeAdmin(session)) {
            return AdminViews.ADMIN_HOME;
        } else {
            return AdminViews.LOGIN_REDIRECT;
        }
    }

    //    Products End Points
    @GetMapping("/products/show.htm")
    public String showAllProducts(HttpSession session, Model model) {
        if (userAuthorizer.authorizeAdmin(session)) {
            model.addAttribute("products", this.productService.getAvailableProducts());
            model.addAttribute("id", 0L);
            return AdminViews.ADMIN_PRODUCTS;
        } else {
            return AdminViews.LOGIN_REDIRECT;
        }
    }

    @GetMapping("/products/update.htm")
    public String updateProduct(HttpSession session, Model model, @RequestParam Long id) {
        if (userAuthorizer.authorizeAdmin(session)) {
            Product selectedProduct;
            try {
                selectedProduct = this.productService.getById(id);
            } catch (GetProductException e) {
                logger.warn(e.getMessage());
                // todo return custom 404 page
                model.addAttribute(PRODUCT_ATTRIBUTE, new Product());
                return AdminViews.ADMIN_UPDATE_PRODUCT;
            }
            model.addAttribute(PRODUCT_ATTRIBUTE, selectedProduct);
            return AdminViews.ADMIN_UPDATE_PRODUCT;
        } else {
            return AdminViews.LOGIN_REDIRECT;
        }
    }


    @PostMapping("/products/update.htm")
    public String submitUpdate(@Valid @ModelAttribute("product") UpdateProductDto product,
                               BindingResult bindingResult,
                               HttpSession session,
                               @RequestParam Long id) {
        if (userAuthorizer.authorizeAdmin(session)) {

            if (bindingResult.hasErrors()) {
                return AdminViews.ADMIN_UPDATE_PRODUCT;
            }

            Product updatedProduct;

            try {
                updatedProduct = this.productService.getById(product.getId());
            } catch (GetProductException e) {
                // todo redirect to 404 custom page error
                logger.warn(e.getMessage());
                return AdminViews.ADMIN_UPDATE_PRODUCT;
            }

            try {
                fileStorageService.storeFile(product.getImage());
                updatedProduct.setImage(product.getImage().getOriginalFilename());

            } catch (FileStorageException | NullPointerException e) {
                logger.warn(e.getMessage());
            }
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setCategory(product.getCategory());
            if (product.getImage() != null)
                updatedProduct.setImage(product.getImage().getOriginalFilename());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setName(product.getName());
            updatedProduct.setInStock(product.getInStock());
            try {
                this.productService.update(updatedProduct);
                return AdminViews.ADMIN_SHOW_PRODUCT_REDIRECT;
            } catch (GetProductException e) {
                return AdminViews.ADMIN_UPDATE_PRODUCT;
            }
        } else {
            return AdminViews.LOGIN_REDIRECT;
        }
    }

    @GetMapping("/products/create.htm")
    public String createProduct(HttpSession session, Model model) {
        if (userAuthorizer.authorizeAdmin(session)) {
            model.addAttribute(PRODUCT_ATTRIBUTE, new CreateProductDto());
            return AdminViews.ADMIN_CREATE_PRODUCT;
        } else {
            return AdminViews.LOGIN_REDIRECT;
        }
    }

    @PostMapping("/products/create.htm")
    public String submitCreate(@Valid @ModelAttribute("product") CreateProductDto product,
                               BindingResult bindingResult,
                               HttpSession session) {
        if (userAuthorizer.authorizeAdmin(session)) {
            if (bindingResult.hasErrors()) {
                return AdminViews.ADMIN_CREATE_PRODUCT;
            }

            Product newProduct = new Product();
            try {
                fileStorageService.storeFile(product.getImage());
                newProduct.setImage(product.getImage().getOriginalFilename());

            } catch (FileStorageException | NullPointerException e) {
                logger.warn(e.getMessage());
            }
            newProduct.setDescription(product.getDescription());
            newProduct.setCategory(product.getCategory());
            if (product.getImage() != null)
                newProduct.setImage(product.getImage().getOriginalFilename());
            newProduct.setPrice(product.getPrice());
            newProduct.setName(product.getName());
            newProduct.setInStock(product.getInStock());
            newProduct.setDeleted(false);
            try {
                this.productService.create(newProduct);
            } catch (CreateProductException e) {
                logger.warn(e.getMessage());
                return AdminViews.ADMIN_CREATE_PRODUCT;
            }
            return AdminViews.ADMIN_SHOW_PRODUCT_REDIRECT;
        } else {
            return AdminViews.LOGIN_REDIRECT;
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
                return AdminViews.UPDATE_ADMIN;
            }
            validator.validate(admin, bindingResult);
            if (bindingResult.hasErrors()) {
                return AdminViews.UPDATE_ADMIN;
            }
            Admin updatedAdmin = new Admin();
            updatedAdmin.setEmail(admin.getEmail());
            updatedAdmin.setUserName(admin.getUserName());
            boolean result = false;
            try {
                adminService.updateAdmin(id, updatedAdmin);
                return AdminViews.ALL_ADMINS_REDIRECT;

            } catch (GetAdminException e) {
                logger.warn(e.getMessage());
                return AdminViews.UPDATE_ADMIN;
            }


        } else {
            return AdminViews.LOGIN_REDIRECT;
        }
    }

    @GetMapping("setAdminPassword.htm")
    public String setAdminPassword() {
        return AdminViews.ADMIN_RESET_PASSWORD;
    }

    @PostMapping("setAdminPassword.htm")
    public String setAdminPassword(@Valid @NotNull @NotBlank @RequestParam("newPassword") String newPassword, HttpSession session) {
        String email = session.getAttribute("email").toString();
        Admin admin = null;
        try {
            admin = adminService.getAdminByEmail(email);
        } catch (GetAdminException e) {
            logger.warn(e.getMessage());
            return AdminViews.ADMIN_RESET_PASSWORD;
        }
        newPassword = hashService.encryptPassword(newPassword, email);
        admin.setPassword(newPassword);
        try {
            adminService.updatePassword(admin.getId(), newPassword);
            return "redirect:/admins/home.htm";
        } catch (GetAdminException e) {
            logger.warn(e.getMessage());
            return AdminViews.ADMIN_RESET_PASSWORD;
        }
    }
}
