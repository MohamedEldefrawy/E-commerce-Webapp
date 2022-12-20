package com.vodafone.controller.rest;

import com.vodafone.controller.AdminRestController;
import com.vodafone.exception.admin.CreateAdminException;
import com.vodafone.exception.admin.GetAdminException;
import com.vodafone.model.Admin;
import com.vodafone.model.Role;
import com.vodafone.model.dto.CreateAdmin;
import com.vodafone.service.AdminService;
import com.vodafone.service.HashService;
import com.vodafone.service.SendEmailService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/admins")
public class AdminController {
    private final AdminService adminService;
    private final HashService hashService;
    private final SendEmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(AdminRestController.class);

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins(){
        List<Admin> admins = new ArrayList<>();
        try {
            admins = adminService.getAll();
            return new ResponseEntity<>(admins, HttpStatus.OK);
        } catch (GetAdminException e) {
            logger.warn(e.getMessage());
            return new ResponseEntity<>(admins, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable Long id){
        Admin admin;
        try {
            admin = adminService.getAdminById(id);
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } catch (GetAdminException e) {
            logger.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id,HttpSession session) {
        Long sessionId = (Long) session.getAttribute("id");
        if (id != 2 && !Objects.equals(sessionId, id)) {
            boolean deleted;
            try {
                deleted = adminService.deleteAdmin(id);
            } catch (GetAdminException e) {
                logger.warn(e.getMessage());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            if (deleted)
                return new ResponseEntity(HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);

    }

    @PostMapping
    public ResponseEntity<Admin> create(@RequestBody CreateAdmin createAdmin, HttpSession session) {
        Admin admin = new Admin();
        admin.setUserName(createAdmin.getUserName());
        admin.setRole(Role.Admin);
        admin.setEmail(createAdmin.getEmail());
        admin.setFirstLogin(true);
        try {
            adminService.createAdmin(admin);
            session.setAttribute("dec_password", admin.getPassword());
            session.setAttribute("newAdminEmail", admin.getEmail());
            //encrypt admin password in db
            String encrypted = hashService.encryptPassword(admin.getPassword(), admin.getEmail());
            admin.setPassword(encrypted);
            adminService.updatePassword(admin.getId(), encrypted); //just set id... impossible to not be set?
            //send email
            //emailService.sendEmail(admin, EmailType.SET_ADMIN_PASSWORD, session);
            //redirect to set password
            return new ResponseEntity<>(adminService.getAdminById(admin.getId()),HttpStatus.CREATED);
        } catch (CreateAdminException e) {
            logger.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Admin> update(@RequestBody CreateAdmin admin, @PathVariable Long id){
        Admin updatedAdmin = new Admin();
        updatedAdmin.setEmail(admin.getEmail());
        updatedAdmin.setUserName(admin.getUserName());
        try {
            Admin adminReturned = adminService.updateAdmin(id, updatedAdmin);
            return new ResponseEntity<>(adminReturned,HttpStatus.OK);

        } catch (GetAdminException e) {
            logger.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
