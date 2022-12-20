package validators;

import com.vodafone.model.Admin;
import com.vodafone.model.Customer;
import com.vodafone.model.Role;
import com.vodafone.model.UserStatus;
import com.vodafone.model.dto.CreateAdmin;
import com.vodafone.service.AdminService;
import com.vodafone.service.CustomerService;
import com.vodafone.validators.AdminValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminValidatorUnitTests {
    /*private AdminValidator adminValidator;
    private AdminService adminService;
    private CustomerService customerService;
    private Admin admin;

    @BeforeEach
    public void setup() {
        adminService = mock(AdminService.class);
        customerService = mock(CustomerService.class);
        adminValidator = new AdminValidator(adminService,customerService);
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
    public void supports_sendCreateAdmin_returnTrue(){
        //Act
        boolean isSupported = adminValidator.supports(CreateAdmin.class);
        //Assert
        assertTrue(isSupported);
    }

    @Test
    public void supports_sendAdmin_returnFalse(){
        //Act
        boolean isSupported = adminValidator.supports(Admin.class);
        //Assert
        assertFalse(isSupported);
    }

    @Test
    public void validate_sendEmptyUsername_addErrors(){
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(1,"","miand@gmail.com");
        Errors errors = new BeanPropertyBindingResult(createAdmin, "createAdmin");
        //Act
        adminValidator.validate(createAdmin,errors);
        //Assert
        assertTrue(errors.hasErrors());
    }
    @Test
    public void validate_sendEmptyEmail_addErrors(){
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(1,"miand","");
        Errors errors = new BeanPropertyBindingResult(createAdmin, "createAdmin");
        //Act
        adminValidator.validate(createAdmin,errors);
        //Assert
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_sendInvalidEmail_addErrors(){
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(1,"miand","mi@gmail.com");
        Errors errors = new BeanPropertyBindingResult(createAdmin, "createAdmin");
        //Act
        adminValidator.validate(createAdmin,errors);
        //Assert
        assertTrue(errors.hasErrors());
    }
    @Test
    public void validate_sendDuplicateAdminEmailAndCreateAdmin_addErrors(){
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(0,"miand","miandmostafa@gmail.com");
        Errors errors = new BeanPropertyBindingResult(createAdmin, "createAdmin");
        when(adminService.getAdminByEmail(any(String.class))).thenReturn(new Admin());
        //Act
        adminValidator.validate(createAdmin,errors);
        //Assert
        assertTrue(errors.hasErrors());
    }
    @Test
    public void validate_sendDuplicateCustomerEmailAndCreateAdmin_addErrors(){
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(0,"miand","miandmostafa@gmail.com");
        Errors errors = new BeanPropertyBindingResult(createAdmin, "createAdmin");
        when(adminService.getAdminByEmail(any(String.class))).thenReturn(null);
        when(customerService.getByMail(anyString())).thenReturn(new Customer());
        //Act
        adminValidator.validate(createAdmin,errors);
        //Assert
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_sendDuplicateAdminUsernameAndCreateAdmin_addErrors(){
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(0,"miand","miandmostafa@gmail.com");
        Errors errors = new BeanPropertyBindingResult(createAdmin, "createAdmin");
        when(adminService.getAdminByEmail(any(String.class))).thenReturn(null);
        when(customerService.getByMail(anyString())).thenReturn(null);
        when(adminService.getAdminByUsername(anyString())).thenReturn(new Admin());
        //Act
        adminValidator.validate(createAdmin,errors);
        //Assert
        assertTrue(errors.hasErrors());
    }
    @Test
    public void validate_sendDuplicateCustomerUsernameAndCreateAdmin_addErrors(){
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(0,"miand","miandmostafa@gmail.com");
        Errors errors = new BeanPropertyBindingResult(createAdmin, "createAdmin");
        when(adminService.getAdminByEmail(any(String.class))).thenReturn(null);
        when(customerService.getByMail(anyString())).thenReturn(null);
        when(adminService.getAdminByUsername(anyString())).thenReturn(null);
        when(customerService.getByUserName(anyString())).thenReturn(new Customer());
        //Act
        adminValidator.validate(createAdmin,errors);
        //Assert
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_sendNoDuplicateInCreate_noErrors(){
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(0,"miand","miandmostafa@gmail.com");
        Errors errors = new BeanPropertyBindingResult(createAdmin, "createAdmin");
        when(adminService.getAdminByEmail(any(String.class))).thenReturn(null);
        when(customerService.getByMail(anyString())).thenReturn(null);
        when(adminService.getAdminByUsername(anyString())).thenReturn(null);
        when(customerService.getByUserName(anyString())).thenReturn(null);
        //Act
        adminValidator.validate(createAdmin,errors);
        //Assert
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validate_sendDuplicateAdminEmailAndUpdateAdmin_addErrors(){
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(2,"miand","miandmostafa@gmail.com");
        Errors errors = new BeanPropertyBindingResult(createAdmin, "createAdmin");
        when(adminService.getAdminById(anyLong())).thenReturn(admin);
        when(adminService.getAdminByEmail(any(String.class))).thenReturn(new Admin());
        //Act
        adminValidator.validate(createAdmin,errors);
        //Assert
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_sendDuplicateCustomerEmailAndUpdateAdmin_addErrors(){
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(2,"miand","miandmostafa@gmail.com");
        Errors errors = new BeanPropertyBindingResult(createAdmin, "createAdmin");
        when(adminService.getAdminById(anyLong())).thenReturn(admin);
        when(adminService.getAdminByEmail(any(String.class))).thenReturn(null);
        when(customerService.getByMail(anyString())).thenReturn(new Customer());
        //Act
        adminValidator.validate(createAdmin,errors);
        //Assert
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_sendDuplicateAdminUsernameAndUpdateAdmin_addErrors(){
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(2,"miand","miandmostafa@gmail.com");
        Errors errors = new BeanPropertyBindingResult(createAdmin, "createAdmin");
        when(adminService.getAdminById(anyLong())).thenReturn(admin);
        when(adminService.getAdminByEmail(any(String.class))).thenReturn(null);
        when(customerService.getByMail(anyString())).thenReturn(null);
        when(adminService.getAdminByUsername(anyString())).thenReturn(new Admin());
        //Act
        adminValidator.validate(createAdmin,errors);
        //Assert
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_sendDuplicateCustomerUsernameAndUpdateAdmin_addErrors(){
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(2,"miand","miandmostafa@gmail.com");
        Errors errors = new BeanPropertyBindingResult(createAdmin, "createAdmin");
        when(adminService.getAdminById(anyLong())).thenReturn(admin);
        when(adminService.getAdminByEmail(any(String.class))).thenReturn(null);
        when(customerService.getByMail(anyString())).thenReturn(null);
        when(adminService.getAdminByUsername(anyString())).thenReturn(null);
        when(customerService.getByUserName(anyString())).thenReturn(new Customer());
        //Act
        adminValidator.validate(createAdmin,errors);
        //Assert
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_sendNoDuplicateInUpdate_noErrors(){
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(2,"miand","miandmostafa@gmail.com");
        Errors errors = new BeanPropertyBindingResult(createAdmin, "createAdmin");
        when(adminService.getAdminById(anyLong())).thenReturn(admin);
        when(adminService.getAdminByEmail(any(String.class))).thenReturn(null);
        when(customerService.getByMail(anyString())).thenReturn(null);
        when(adminService.getAdminByUsername(anyString())).thenReturn(null);
        when(customerService.getByUserName(anyString())).thenReturn(null);
        //Act
        adminValidator.validate(createAdmin,errors);
        //Assert
        assertFalse(errors.hasErrors());
    }

     */
}
