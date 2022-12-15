package service;

import com.vodafone.exception.IncompleteUserAttributesException;
import com.vodafone.exception.NullCustomerException;
import com.vodafone.exception.NullIdException;
import com.vodafone.model.Customer;
import com.vodafone.model.Role;
import com.vodafone.model.UserStatus;
import com.vodafone.repository.customer.CustomerRepository;
import com.vodafone.repository.customer.ICustomerRepository;
import com.vodafone.service.CustomerService;
import com.vodafone.service.HashService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerServiceUnitTest {
    private static final ICustomerRepository customerRepositoryMock = mock(CustomerRepository.class);
    private static final HashService hashService = mock(HashService.class);
    private static final CustomerService customerService = new CustomerService(customerRepositoryMock, hashService);
    private final Logger logger = LoggerFactory.getLogger(CustomerServiceUnitTest.class);

    @Test
    public void createCustomer_nonNullEntity_expectTrue() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        customer.setPassword("12345678");
        customer.setRole(Role.Customer);
        customer.setUserStatus(UserStatus.ACTIVATED);
        when(customerRepositoryMock.create(customer)).thenReturn(Optional.of(1L));
        //Act
        boolean isCustomerCreatedSuccessfully = customerService.create(customer);
        //Assert
        assertTrue(isCustomerCreatedSuccessfully);
    }

    @Test
    public void createCustomer_NullEntity_expectNullCustomerException() {
        //Arrange
        when(customerRepositoryMock.create(null)).thenThrow(new NullCustomerException("Null customer entity is provided"));
        //Act
        assertThrows(NullCustomerException.class, () -> customerService.create(null));
    }

    @Test
    public void createCustomer_NullEntity_expectIncompleteUserAttributesException() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        customer.setRole(Role.Customer);
        customer.setUserStatus(UserStatus.ACTIVATED);
        when(customerRepositoryMock.create(customer)).thenThrow(new IncompleteUserAttributesException("Customer Data is not completed"));
        //Act
        assertThrows(IncompleteUserAttributesException.class, () -> customerService.create(customer));
    }

    @Test
    public void updateCustomer_nonNullEntity_expectTrue() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        customer.setPassword("12345678");
        customer.setRole(Role.Customer);
        customer.setUserStatus(UserStatus.ACTIVATED);
        when(customerRepositoryMock.getById(1L)).thenReturn(Optional.of(new Customer()));
        when(customerRepositoryMock.update(1L, customer)).thenReturn(true);
        //Act
        boolean isCustomerUpdatedSuccessfully = customerService.update(1L, customer);
        //Assert
        assertTrue(isCustomerUpdatedSuccessfully);
    }

    @Test
    public void updateCustomer_NullEntity_expectNullCustomerException() {
        //Arrange

        when(customerRepositoryMock.getById(1L)).thenReturn(Optional.of(new Customer()));
        when(customerRepositoryMock.update(1L, null)).thenThrow(new NullCustomerException("Null customer entity is provided"));
        //Act
        assertThrows(NullCustomerException.class, () -> customerService.update(1L, null));
    }

    @Test
    public void updateCustomer_NullId_expectNullCustomerException() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        customer.setPassword("12345678");
        customer.setRole(Role.Customer);
        customer.setUserStatus(UserStatus.ACTIVATED);
        when(customerRepositoryMock.update(null, customer)).thenThrow(new NullIdException("Null customer id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> customerService.update(null, customer));
    }

    @Test
    public void updateCustomerStatusToActivated_existCustomerEmail_expectTrue() {
        Customer customer = new Customer();
        String email = "mohammedre4a@gmail.com";
        customer.setEmail(email);
        when(customerRepositoryMock.getByMail(email)).thenReturn(Optional.of(customer));
        when(customerRepositoryMock.updateStatusActivated(customer)).thenReturn(true);
        //Act
        boolean isCustomerActivated = customerService.updateStatusActivated(customer.getEmail());
        //Assert
        assertTrue(isCustomerActivated);
    }

    @Test
    public void updateCustomerStatusToActivated_nullCustomerEmail_expectNullCustomerException() {
        when(customerRepositoryMock.updateStatusActivated(null)).thenThrow(new NullPointerException("Null email is provided"));
        when(customerRepositoryMock.getByMail("mohammedre4a@gmail.com")).thenReturn(Optional.of(new Customer()));
        //Act
        assertThrows(NullPointerException.class, () -> customerService.updateStatusActivated(null));
    }

    @Test
    public void updateCustomerStatusToActivated_nullEmail_expectNullPointerException() {
        when(customerRepositoryMock.updateStatusActivated(new Customer())).thenThrow(new NullPointerException("Null email is provided"));
        //Act
        assertThrows(NullPointerException.class, () -> customerService.updateStatusActivated(null));
    }

    @Test
    public void deleteCustomer_nonNullEntity_expectTrue() {
        //Arrange
        when(customerRepositoryMock.delete(1L)).thenReturn(true);
        when(customerRepositoryMock.getById(1L)).thenReturn(Optional.of(new Customer()));

        //Act
        boolean isCustomerUpdatedSuccessfully = customerService.delete(1L);
        //Assert
        assertTrue(isCustomerUpdatedSuccessfully);
    }

    @Test()
    public void deleteCustomer_NullId_expectNullIdException() {
        //Arrange
        when(customerRepositoryMock.delete(null)).thenThrow(new NullIdException("Null customer id is provided"));
        when(customerRepositoryMock.getById(1L)).thenReturn(Optional.of(new Customer()));

        //Act
        assertThrows(NullIdException.class, () -> customerService.delete(null));
    }

    @Test
    public void getCustomer_nonNullId_expectNonNullCustomerObject() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        customer.setPassword("12345678");
        customer.setRole(Role.Customer);
        customer.setUserStatus(UserStatus.ACTIVATED);
        when(customerRepositoryMock.getById(1L)).thenReturn(Optional.of(customer));
        //Act
        Customer customerObj = customerService.getById(1L);
        //Assert
        assertNotNull(customerObj);
    }

    @Test
    public void getCustomer_NullId_expectNullIdException() {
        //Arrange
        when(customerRepositoryMock.getById(null)).thenThrow(new NullIdException("Null customer id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> customerService.getById(null));
    }

    @Test
    public void getByMail_existingEmail_expectNonNullCustomer() {
        //Arrange
        String email = "mi@gmail.com";
        when(customerRepositoryMock.getByMail(email)).thenReturn(Optional.of(new Customer()));
        //Act
        Customer customer = customerService.getByMail(email);
        //Assert
        assertNotNull(customer);
    }

    @Test
    public void getByMail_nullEmail_expectNonNullCustomer() {
        //Arrange
        when(customerRepositoryMock.getByMail(null)).thenThrow(new NullPointerException("Null customer email is provided"));
        //Act
        assertThrows(NullPointerException.class, () -> customerService.getByMail(null));
    }

    @Test
    public void getByMail_nonExistingEmail_expectNonNullCustomer() {
        //Arrange
        String email = "fasarwas@gmail.com";
        when(customerRepositoryMock.getByMail(anyString())).thenReturn(Optional.empty());
        //Act
        assertThrows(NullCustomerException.class, () -> customerService.getByMail(email));
    }

    @Test
    public void getByUsername_existingUsername_expectNonNullCustomer() {
        //Arrange
        String username = "mi";
        when(customerRepositoryMock.getByUserName(any(String.class))).thenReturn(Optional.of(new Customer()));
        //Act
        Customer customer = customerService.getByUserName(username);
        //Assert
        assertNotNull(customer);
    }

    @Test
    public void getByUsername_nullUsername_expectNonNullCustomer() {
        //Arrange
        when(customerRepositoryMock.getByUserName(anyString())).thenThrow(new NullPointerException("Null customer username is provided"));
        //Act
        assertThrows(NullPointerException.class, () -> customerService.getByUserName(null));
    }

    @Test
    public void getByUsername_nonExistingUsername_expectNonNullCustomer() {
        //Arrange
        String username = "null username";
        when(customerRepositoryMock.getByUserName(anyString())).thenReturn(Optional.empty());
        //Act
        assertThrows(NullCustomerException.class, () -> customerService.getByUserName(username));
    }

    @Test
    public void resetPassword_nonNullEmailAndPassword_expectTrue() {
        //Arrange
        String email = "mi@gmail.com";
        String password = "12345678";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        Optional<Customer> customerOptional = Optional.of(customer);
        when(customerRepositoryMock.getByMail(anyString())).thenReturn(customerOptional);
        when(customerRepositoryMock.resetPassword(customerOptional.get(), password)).thenReturn(true);
        //Act
        boolean isPasswordChanged = customerService.resetPassword(email, password);
        //Assert
        assertTrue(isPasswordChanged);
    }

    @Test
    public void resetPassword_nullEmailAndNonNullPassword_expectException() {
        //Arrange
        String password = "12345678";
        //Act
        assertThrows(NullPointerException.class, () -> customerService.resetPassword(null, password));
    }

    @Test
    public void resetPassword_nonNullEmailAndNullPassword_expectException() {
        //Arrange
        String email = "mi@gmail.com";
        Optional<Customer> customer = Optional.of(new Customer());
        when(customerRepositoryMock.getByMail(email)).thenReturn(customer);
        when(customerRepositoryMock.resetPassword(customer.get(), null)).thenThrow(new NullPointerException("Null customer password is provided"));
        //Act
        assertThrows(NullPointerException.class, () -> customerService.resetPassword(email, null));
    }

    @Test
    public void resetPassword_nullCustomer_expectException() {
        //Arrange
        String email = "mi@gmail.com";
        String password = "12345678";
        Optional<Customer> customer = Optional.empty();
        when(customerRepositoryMock.getByMail(email)).thenReturn(customer);
        when(customerRepositoryMock.resetPassword(null, password)).thenThrow(new NullCustomerException("Null customer password is provided"));
        //Act
        assertThrows(NullCustomerException.class, () -> customerService.resetPassword(email, password));
    }

    @Test
    public void expireOtp_nonNullUsername_expectTrue() {
        //Arrange
        String username = "mi";
        Optional<Customer> customer = Optional.of(new Customer());
        when(customerRepositoryMock.getByUserName(username)).thenReturn(customer);
        when(customerRepositoryMock.expireOtp(customer.get())).thenReturn(true);
        //Act
        boolean isCodeReset = customerService.expireOtp(username);
        //Assert
        assertTrue(isCodeReset);
    }

    @Test
    public void expireOtp_nullUsername_expectNullPointerException() {
        //Arrange
        String username = null;
        //Act
        assertThrows(NullPointerException.class, () -> customerService.expireOtp(username));
    }

    @Test
    public void expireOtp_nullCustomer_expectNullCustomerException() {
        //Arrange
        String username = "mi";
        Optional<Customer> customer = Optional.empty();
        when(customerRepositoryMock.getByUserName(anyString())).thenReturn(customer);
        when(customerRepositoryMock.expireOtp(null)).thenThrow(new NullCustomerException("Customer not found with username provided"));
        //Act
        assertThrows(NullCustomerException.class, () -> customerService.expireOtp(username));
    }
}
