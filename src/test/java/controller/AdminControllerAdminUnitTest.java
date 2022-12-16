package controller;

import com.vodafone.controller.AdminController;
import com.vodafone.exception.admin.CreateAdminException;
import com.vodafone.exception.admin.GetAdminException;
import com.vodafone.model.Admin;
import com.vodafone.model.Role;
import com.vodafone.model.UserStatus;
import com.vodafone.model.dto.CreateAdmin;
import com.vodafone.service.AdminService;
import com.vodafone.service.HashService;
import com.vodafone.service.ProductService;
import com.vodafone.service.SendEmailService;
import com.vodafone.validators.AdminValidator;
import com.vodafone.validators.UserAuthorizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


class AdminControllerAdminUnitTest {

    private AdminService adminService;
    private UserAuthorizer userAuthorizer;
    private AdminValidator validator;
    private HashService hashService;
    private AdminController adminController;
    private HttpSession session;
    private Admin admin;


    @BeforeEach
    void setUp() {
        adminService = mock(AdminService.class);
        ProductService productService = mock(ProductService.class);
        userAuthorizer = mock(UserAuthorizer.class);
        validator = mock(AdminValidator.class);
        hashService = mock(HashService.class);
        SendEmailService emailService = mock(SendEmailService.class);
        session = mock(HttpSession.class);
        adminController = new AdminController(adminService,productService,userAuthorizer,validator,
                hashService,emailService);
        admin = new Admin(); //create new admin
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
    }

    @Test
    void getAll_sendUnAuthorizedAdmin_getLoginPage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(false);
        //Act
        String page = adminController.getAll(session,model);
        //Assert
        assertEquals("redirect:/login.htm",page);
    }
    @Test
    void getAll_throwGetAdminException_getAdminsPage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        when(adminService.getAll()).thenThrow(GetAdminException.class);
        when(model.addAttribute(any(),any())).thenReturn(model);
        //Act
        String page = adminController.getAll(session,model);
        //Assert
        verify(adminService,times(1)).getAll();
        assertEquals("admin/viewAllAdmins",page);

    }
    @Test
    void getAll_getAdminsList_getHomePage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        List<Admin> admins= new ArrayList<>();
        when(adminService.getAll()).thenReturn(admins);
        //Act
        String page = adminController.getAll(session,model);
        //Assert
        verify(adminService,times(1)).getAll();
        assertEquals("admin/viewAllAdmins",page);
    }

    @Test
    void delete_sendUnAuthorizedAdmin_get401() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(false);
        //Act
        String page = adminController.delete(session,1L);
        //Assert
        assertEquals("401",page);
    }

    @Test
    void delete_sendSuperAdminId_get405() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        //Act
        String page = adminController.delete(session,2L);
        //Assert
        assertEquals("405",page);
    }

    @Test
    void delete_sendOwnId_get405() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(3L);
        //Act
        String page = adminController.delete(session,3L);
        //Assert
        assertEquals("405",page);
    }
    @Test
    void delete_sendIdToBeDeletedWithServerError_get500() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(2L);
        when(adminService.deleteAdmin(any())).thenReturn(false);
        //Act
        String page = adminController.delete(session,3L);
        //Assert
        assertEquals("500",page);
    }
    @Test
    void delete_sendIdToBeDeletedThrowException_get404() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(2L);
        when(adminService.deleteAdmin(any())).thenThrow(GetAdminException.class);
        //Act
        String page = adminController.delete(session,3L);
        //Assert
        assertEquals("500",page);
    }
    @Test
    void delete_sendIdToBeDeleted_get200() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(2L);
        when(adminService.deleteAdmin(any())).thenReturn(true);
        //Act
        String page = adminController.delete(session,3L);
        //Assert
        assertEquals("200",page);
    }

    @Test
    void updateAdminPage_sendUnauthorizedAdmin_getLoginPage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(false);
        //Act
        String page = adminController.updateAdminPage(session,model,1L);
        //Assert
        assertEquals("redirect:/login.htm",page);
    }

    @Test
    void updateAdminPage_sendAuthorizedAdminAndNonExistentId_getAdminsPage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        when(adminService.getAdminById(any())).thenThrow(GetAdminException.class);
        //Act
        String page = adminController.updateAdminPage(session,model,1L);
        //Assert
        assertEquals("admin/viewAllAdmins",page);
    }

    @Test
    void updateAdminPage_sendAuthorizedAdminAndExistingId_getUpdatePage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        when(adminService.getAdminById(any())).thenReturn(admin);
        //Act
        String page = adminController.updateAdminPage(session,model,1L);
        //Assert
        assertEquals("admin/updateAdmin",page);
    }

    @Test
    void getCreateAdminPage_sendUnauthorizedAdmin_getLoginPage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(false);
        //Act
        String page = adminController.getCreateAdminPage(session,model);
        //Assert
        assertEquals("redirect:/login.htm",page);
    }

    @Test
    void getCreateAdminPage_sendAuthorizedAdmin_getCreatePage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        //Act
        String page = adminController.getCreateAdminPage(session,model);
        //Assert
        assertEquals("admin/createAdmin",page);
    }

    @Test
    void create_sendUnauthorizedAdmin_getLoginPage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(false);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        //Act
        String page = adminController.create(createAdmin,session,bindingResult);
        //Assert
        assertEquals("redirect:/login.htm",page);
    }

    @Test
    void create_sendBindingErrors_getCreatePage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        //Act
        String page = adminController.create(createAdmin,session,bindingResult);
        //Assert
        assertEquals("admin/createAdmin",page);
    }

    @Test
    void create_sendValidationErrors_getCreatePage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        FieldError fieldError = mock(FieldError.class);
        //add errors to binding result
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            ((BindingResult)args[1]).addError(fieldError);
            return null; // void method in a block-style lambda, so return null
        }).when(validator).validate(createAdmin,bindingResult);
        //Act
        String page = adminController.create(createAdmin,session,bindingResult);
        //Assert
        assertEquals("admin/createAdmin",page);
    }

    @Test
    void create_sendCreateException_getCreatePage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(adminService.createAdmin(any())).thenThrow(CreateAdminException.class);
        //Act
        String page = adminController.create(createAdmin,session,bindingResult);
        //Assert
        assertEquals("admin/createAdmin",page);
    }

    @Test
    void create_sendCreateNoException_getAdminsPage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(adminService.createAdmin(any())).thenReturn(true);
        //Act
        String page = adminController.create(createAdmin,session,bindingResult);
        //Assert
        assertEquals("redirect:/admins/admins.htm",page);
    }

    @Test
    void update_sendUnauthorizedAdmin_getLoginPage() {
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(false);
        //Act
        String page = adminController.updateAdmin(createAdmin,session,bindingResult,3L);
        //Assert
        assertEquals("redirect:/login.htm",page);
    }

    @Test
    void update_sendBindingErrors_getUpdatePage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        //Act
        String page = adminController.updateAdmin(createAdmin,session,bindingResult,3L);
        //Assert
        assertEquals("admin/updateAdmin",page);
    }

   @Test
    void update_sendValidationErrors_getUpdatePage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        FieldError fieldError = mock(FieldError.class);
        //add errors to binding result
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            ((BindingResult)args[1]).addError(fieldError);
            return null; // void method in a block-style lambda, so return null
        }).when(validator).validate(createAdmin,bindingResult);
        //Act
        String page = adminController.updateAdmin(createAdmin,session,bindingResult,3L);
        //Assert
        assertEquals("admin/updateAdmin",page);
    }

    @Test
    void update_sendInvalidId_getUpdatePage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(adminService.updateAdmin(any(),any())).thenThrow(GetAdminException.class);
        //Act
        String page = adminController.updateAdmin(createAdmin,session,bindingResult,3L);
        //Assert
        assertEquals("admin/updateAdmin",page);
    }

    @Test
    void update_sendValidId_getAdminsPage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(adminService.updateAdmin(any(),any())).thenReturn(true);
        //Act
        String page = adminController.updateAdmin(createAdmin,session,bindingResult,3L);
        //Assert
        assertEquals("redirect:/admins/admins.htm",page);
    }

    @Test
    void setAdminPassword() {
        //Act
        String page = adminController.setAdminPassword();
        //Assert
        assertEquals("admin/setAdminPassword",page);
    }

    @Test
    void testSetAdminPassword_sendInvalidEmail_getResetPage() {
        //Arrange
        when(session.getAttribute(anyString())).thenReturn(String.class);
        when(adminService.getAdminByEmail(anyString())).thenThrow(GetAdminException.class);
        //Act
        String page = adminController.setAdminPassword("29019280192",session);
        //Assert
        assertEquals("admin/setAdminPassword",page);
    }
    @Test
    void testSetAdminPassword_sendValidEmailAndInvalidId_getResetPage() {
        //Arrange
        when(session.getAttribute(any())).thenReturn(String.class);
        when(adminService.getAdminByEmail(anyString())).thenReturn(admin);
        when(adminService.updatePassword(any(),any())).thenThrow(GetAdminException.class);
        when(hashService.encryptPassword(any(),any())).thenReturn("12jo2jj1oij2");
        //Act
        String page = adminController.setAdminPassword("29019280192",session);
        //Assert
        assertEquals("admin/setAdminPassword",page);
    }

    @Test
    void testSetAdminPassword_sendValidEmailAndValidId_getHomePage() {
        //Arrange
        when(session.getAttribute(any())).thenReturn(String.class);
        when(adminService.getAdminByEmail(anyString())).thenReturn(admin);
        when(adminService.updatePassword(any(),any())).thenReturn(true);
        when(hashService.encryptPassword(any(),any())).thenReturn("12jo2jj1oij2");
        //Act
        String page = adminController.setAdminPassword("29019280192",session);
        //Assert
        assertEquals("redirect:/admins/home.htm",page);
    }
}