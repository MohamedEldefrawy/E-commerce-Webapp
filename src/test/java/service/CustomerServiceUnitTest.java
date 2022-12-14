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
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerServiceUnitTest {
    private static ICustomerRepository customerRepositoryMock;
    private static HashService hashService;
    private static CustomerService customerService;

    @Before
    public void init() {
//        MockitoAnnotations.openMocks(this);
        hashService = mock(HashService.class);
        customerRepositoryMock = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepositoryMock, hashService);
    }

    @Test
    public void createCustomer_nonNullEntity_expectTrue() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        customer.setPassword("12345678");
        customer.setRole(Role.Customer);
        customer.setUserStatus(UserStatus.ACTIVATED);
        when(customerRepositoryMock.create(customer)).thenReturn(true);
        //Act
        boolean isCustomerCreatedSuccessfully = customerService.create(customer);
        //Assert
        assertTrue(isCustomerCreatedSuccessfully);
    }

    @Test(expected = NullCustomerException.class)
    public void createCustomer_NullEntity_expectNullCustomerException() {
        //Arrange
        when(customerRepositoryMock.create(null)).thenThrow(new NullCustomerException("Null customer entity is provided"));
        //Act
        customerService.create(null);
    }

    @Test(expected = IncompleteUserAttributesException.class)
    public void createCustomer_NullEntity_expectIncompleteUserAttributesException() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        customer.setRole(Role.Customer);
        customer.setUserStatus(UserStatus.ACTIVATED);
        when(customerRepositoryMock.create(customer)).thenThrow(new IncompleteUserAttributesException("Customer Data is not completed"));
        //Act
        customerService.create(customer);
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
        when(customerService.get(1L)).thenReturn(new Customer());
        when(customerRepositoryMock.update(1L, customer)).thenReturn(true);
        //Act
        boolean isCustomerUpdatedSuccessfully = customerService.update(1L, customer);
        //Assert
        assertTrue(isCustomerUpdatedSuccessfully);
    }

    @Test(expected = NullCustomerException.class)
    public void updateCustomer_NullEntity_expectNullCustomerException() {
        //Arrange

        when(customerService.get(1L)).thenReturn(new Customer());
        when(customerRepositoryMock.update(1L, null)).thenThrow(new NullCustomerException("Null customer entity is provided"));
        //Act
        customerService.update(1L, null);
    }

    @Test(expected = NullIdException.class)
    public void updateCustomer_NullId_expectNullCustomerException() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        customer.setPassword("12345678");
        customer.setRole(Role.Customer);
        customer.setUserStatus(UserStatus.ACTIVATED);
        when(customerRepositoryMock.update(null, customer)).thenThrow(new NullIdException("Null customer id is provided"));
        when(customerService.get(null)).thenThrow(new NullCustomerException("Customer not found with provided id"));
        //Act
        customerService.update(null, customer);
    }

    @Test
    public void updateCustomerStatusToActivated_existCustomerEmail_expectTrue() {
        Customer customer = new Customer();
        String email = "mohammedre4a@gmail.com";
        customer.setEmail(email);
        when(customerRepositoryMock.getByMail(email)).thenReturn(customer);
        when(customerRepositoryMock.updateStatusActivated(customer.getEmail())).thenReturn(true);
        //Act
        boolean isCustomerActivated = customerService.updateStatusActivated(customer.getEmail());
        //Assert
        assertTrue(isCustomerActivated);
    }

    @Test(expected = NullPointerException.class)
    public void updateCustomerStatusToActivated_nullCustomerEmail_expectFalse() {
        when(customerRepositoryMock.updateStatusActivated(null)).thenThrow(new NullPointerException("Null email is provided"));
        when(customerRepositoryMock.getByMail("mohammedre4a@gmail.com")).thenReturn(new Customer());
        //Act
        boolean isCustomerActivated = customerService.updateStatusActivated(null);
        //Assert
        assertTrue(isCustomerActivated);
    }

    @Test
    public void deleteCustomer_nonNullEntity_expectTrue() {
        //Arrange
        when(customerRepositoryMock.delete(1L)).thenReturn(true);
        when(customerService.get(1L)).thenReturn(new Customer());

        //Act
        boolean isCustomerUpdatedSuccessfully = customerService.delete(1L);
        //Assert
        assertTrue(isCustomerUpdatedSuccessfully);
    }

    @Test(expected = NullIdException.class)
    public void deleteCustomer_NullId_expectNullIdException() {
        //Arrange
        when(customerRepositoryMock.delete(null)).thenThrow(new NullIdException("Null customer id is provided"));
        when(customerService.get(1L)).thenReturn(new Customer());

        //Act
        customerService.delete(null);
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
        when(customerRepositoryMock.get(1L)).thenReturn(customer);
        //Act
        Customer customerObj = customerService.get(1L);
        //Assert
        assertNotNull(customerObj);
    }

    @Test(expected = NullIdException.class)
    public void getCustomer_NullId_expectNullIdException() {
        //Arrange
        when(customerRepositoryMock.get(null)).thenThrow(new NullIdException("Null customer id is provided"));
        //Act
        customerService.get(null);
    }

    @Test
    public void getByMail_existingEmail_expectNonNullCustomer() {
        //Arrange
        String email = "mi@gmail.com";
        when(customerRepositoryMock.getByMail(any(String.class))).thenReturn(new Customer());
        //Act
        Customer customer = customerService.getByMail(email);
        //Assert
        assertNotNull(customer);
    }

    @Test(expected = NullPointerException.class)
    public void getByMail_nullEmail_expectNonNullCustomer() {
        //Arrange
        when(customerRepositoryMock.getByMail(null)).thenThrow(new NullPointerException("Null customer email is provided"));
        //Act
        customerService.getByMail(null);
    }

    @Test
    public void getByMail_nonExistingEmail_expectNonNullCustomer() {
        //Arrange
        String email = "fasarwas@gmail.com";
        when(customerRepositoryMock.getByMail(anyString())).thenReturn(null);
        //Act
        Customer customer = customerService.getByMail(email);
        //Assert
        assertNull(customer);
    }

    @Test
    public void getByUsername_existingUsername_expectNonNullCustomer() {
        //Arrange
        String username = "mi";
        when(customerRepositoryMock.getByUserName(any(String.class))).thenReturn(new Customer());
        //Act
        Customer customer = customerService.getByUserName(username);
        //Assert
        assertNotNull(customer);
    }

    @Test(expected = NullPointerException.class)
    public void getByUsername_nullUsername_expectNonNullCustomer() {
        //Arrange
        when(customerRepositoryMock.getByUserName(anyString())).thenThrow(new NullPointerException("Null customer username is provided"));
        //Act
        customerService.getByUserName(null);
    }

    @Test
    public void getByUsername_nonExistingUsername_expectNonNullCustomer() {
        //Arrange
        String username = "null username";
        when(customerRepositoryMock.getByUserName(anyString())).thenReturn(null);
        //Act
        Customer customer = customerService.getByUserName(username);
        //Assert
        assertNull(customer);
    }

    @Test
    public void resetPassword_nonNullEmailAndPassword_expectTrue() {
        //Arrange
        String email = "mi@gmail.com";
        String password = "12345678";
        when(customerRepositoryMock.getByMail(email)).thenReturn(new Customer());
        when(customerRepositoryMock.resetPassword(email, password)).thenReturn(true);
        //Act
        boolean isPasswordChanged = customerService.resetPassword(email, password);
        //Assert
        assertTrue(isPasswordChanged);
    }

    @Test(expected = NullPointerException.class)
    public void resetPassword_nullEmailAndNonNullPassword_expectException() {
        //Arrange
        String password = "12345678";
        when(customerRepositoryMock.getByMail(null)).thenThrow(new NullPointerException("Null customer email is provided"));
        when(customerRepositoryMock.resetPassword(null, password)).thenThrow(new NullPointerException("Null customer email is provided"));
        //Act
        customerService.resetPassword(null, password);
    }

    @Test(expected = NullPointerException.class)
    public void resetPassword_nonNullEmailAndNullPassword_expectException() {
        //Arrange
        String email = "mi@gmail.com";
        when(customerRepositoryMock.getByMail(email)).thenThrow(new NullPointerException("Null customer password is provided"));
        when(customerRepositoryMock.resetPassword(email, null)).thenThrow(new NullPointerException("Null customer password is provided"));
        //Act
        customerService.resetPassword(email, null);
    }

    @Test(expected = NullCustomerException.class)
    public void resetPassword_nullCustomer_expectException() {
        //Arrange
        String email = "mi@gmail.com";
        String password = "12345678";
        when(customerRepositoryMock.getByMail(email)).thenReturn(null);
        when(customerRepositoryMock.resetPassword(email, password)).thenThrow(new NullCustomerException("Null customer password is provided"));
        //Act
        customerService.resetPassword(email, password);
    }

    @Test
    public void expireOtp_nonNullUsername_expectTrue() {
        //Arrange
        String username = "mi";
        when(customerRepositoryMock.getByUserName(username)).thenReturn(new Customer());
        when(customerRepositoryMock.expireOtp(username)).thenReturn(true);
        //Act
        boolean isCodeReset = customerService.expireOtp(username);
        //Assert
        assertTrue(isCodeReset);
    }

    @Test(expected = NullPointerException.class)
    public void expireOtp_nullUsername_expectNullPointerException() {
        //Arrange
        when(customerRepositoryMock.getByUserName(null)).thenThrow(new NullPointerException("Null username is provided"));
        when(customerRepositoryMock.expireOtp(null)).thenThrow(new NullPointerException("Null username is provided"));
        //Act
        customerService.expireOtp(null);
    }

    @Test(expected = NullCustomerException.class)
    public void expireOtp_nullCustomer_expectNullPointerException() {
        //Arrange
        String username = "mi";
        when(customerRepositoryMock.getByUserName(username)).thenReturn(null);
        when(customerRepositoryMock.expireOtp(null)).thenThrow(new NullCustomerException("Customer not found with username provided"));
        //Act
        customerService.expireOtp(username);
    }
}
