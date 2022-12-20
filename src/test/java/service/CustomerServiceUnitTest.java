package service;

import com.vodafone.exception.NullIdException;
import com.vodafone.exception.customer.IncompleteUserAttributesException;
import com.vodafone.exception.customer.NullCustomerException;
import com.vodafone.model.Customer;
import com.vodafone.model.Role;
import com.vodafone.model.UserStatus;
import com.vodafone.repository.customer.CustomerRepository;
import com.vodafone.repository.customer.ICustomerRepository;
import com.vodafone.service.CustomerService;
import com.vodafone.service.HashService;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CustomerServiceUnitTest {
    /*private static final ICustomerRepository customerRepositoryMock = mock(CustomerRepository.class);
    private static final HashService hashService = mock(HashService.class);
    private static final CustomerService customerService = new CustomerService(customerRepositoryMock, hashService);
    private final Logger logger = LoggerFactory.getLogger(CustomerServiceUnitTest.class);

    @Test
    void createCustomer_nonNullEntity_expectTrue() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        customer.setPassword("12345678");
        customer.setRole(Role.Customer);
        customer.setUserStatus(UserStatus.ACTIVATED);
        when(customerRepositoryMock.save(customer)).thenReturn(customer);
        //Act
        boolean isCustomerCreatedSuccessfully = customerService.create(customer) != null;
        //Assert
        assertTrue(isCustomerCreatedSuccessfully);
    }

    @Test
    void createCustomer_NullEntity_expectNullCustomerException() {
        //Arrange
        Customer customer = null;
        //Act
        assertThrows(NullCustomerException.class, () -> customerService.create(customer));
    }

    @Test
    void createCustomer_NullEntity_expectIncompleteUserAttributesException() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        customer.setRole(Role.Customer);
        customer.setUserStatus(UserStatus.ACTIVATED);
        when(customerRepositoryMock.save(customer)).thenThrow(new IncompleteUserAttributesException("Customer Data is not completed"));
        //Act
        assertThrows(IncompleteUserAttributesException.class, () -> customerService.create(customer));
    }

    @Test
    void updateCustomer_nonNullEntity_expectTrue() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        customer.setPassword("12345678");
        customer.setRole(Role.Customer);
        customer.setUserStatus(UserStatus.ACTIVATED);
        when(customerRepositoryMock.findById(1L)).thenReturn(Optional.of(new Customer()));
        when(customerRepositoryMock.save(customer)).thenReturn(customer);
        //Act
        boolean isCustomerUpdatedSuccessfully = customerService.update(1L, customer) != null;
        //Assert
        assertTrue(isCustomerUpdatedSuccessfully);
    }

    @Test
    void updateCustomer_NullEntity_expectNullCustomerException() {
        //Arrange

        when(customerRepositoryMock.findById(1L)).thenReturn(Optional.of(new Customer()));
        //Act
        assertThrows(NullCustomerException.class, () -> customerService.update(1L, null));
    }

    @Test
    void updateCustomer_NullId_expectNullCustomerException() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        customer.setPassword("12345678");
        customer.setRole(Role.Customer);
        customer.setUserStatus(UserStatus.ACTIVATED);
        //Act
        assertThrows(NullIdException.class, () -> customerService.update(null, customer));
    }

    @Test
    void updateCustomer_notExistingCustomer_expectHibernateException() {
        //Arrange
        when(customerRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> customerService.update(anyLong(), new Customer()));
    }

    @Test
    void updateCustomerStatusToActivated_existCustomerEmail_expectTrue() {
        Customer customer = new Customer();
        String email = "mohammedre4a@gmail.com";
        customer.setEmail(email);
        when(customerRepositoryMock.findCustomerByEmail(email)).thenReturn(Optional.of(customer));
        when(customerRepositoryMock.updateStatusActivated(customer)).thenReturn(true);
        //Act
        boolean isCustomerActivated = customerService.updateStatusActivated(customer.getEmail());
        //Assert
        assertTrue(isCustomerActivated);
    }

    @Test
    void updateCustomerStatusToActivated_nullCustomerEmail_expectNullCustomerException() {
        when(customerRepositoryMock.updateStatusActivated(null)).thenThrow(new NullPointerException("Null email is provided"));
        when(customerRepositoryMock.findCustomerByEmail("mohammedre4a@gmail.com")).thenReturn(Optional.of(new Customer()));
        //Act
        assertThrows(NullPointerException.class, () -> customerService.updateStatusActivated(null));
    }

    @Test
    void updateCustomerStatusToActivated_nullEmail_expectNullPointerException() {
        when(customerRepositoryMock.updateStatusActivated(new Customer())).thenThrow(new NullPointerException("Null email is provided"));
        //Act
        assertThrows(NullPointerException.class, () -> customerService.updateStatusActivated(null));
    }

    @Test
    void updateCustomerStatusToActivated_notExistingCustomer_expectHibernateException() {
        //Arrange
        when(customerRepositoryMock.findCustomerByEmail(anyString())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> customerService.updateStatusActivated(anyString()));
    }

    @Test
    void deleteCustomer_nonNullEntity_expectTrue() {
        //Arrange
        Customer customer = new Customer();
        when(customerRepositoryMock.findById(1L)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepositoryMock.delete(customer));

        //Act
        customerService.delete(1L);
        boolean isCustomerDeletedSuccessfully = customerService.delete(1L);
        //Assert
        assertTrue(isCustomerDeletedSuccessfully);
    }

    @Test
    void deleteCustomer_NullId_expectNullIdException() {
        //Arrange
        Long id = null;
        //Act
        assertThrows(NullIdException.class, () -> customerService.delete(id));
    }

    @Test
    void deleteCustomer_notExistingCustomer_expectHibernateException() {
        //Arrange
        when(customerRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> customerService.delete(anyLong()));
    }

    @Test
    void getCustomer_nonNullId_expectNonNullCustomerObject() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        customer.setPassword("12345678");
        customer.setRole(Role.Customer);
        customer.setUserStatus(UserStatus.ACTIVATED);
        when(customerRepositoryMock.findById(1L)).thenReturn(Optional.of(customer));
        //Act
        Customer customerObj = customerService.findCustomerById(1L);
        //Assert
        assertNotNull(customerObj);
    }

    @Test
    void getCustomer_NullId_expectNullIdException() {
        //Arrange
        Long id = null;
        //Act
        assertThrows(NullIdException.class, () -> customerService.findCustomerById(id));
    }

    @Test
    void getCustomer_notExistingCustomer_expectHibernateException() {
        //Arrange
        when(customerRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> customerService.findCustomerById(anyLong()));
    }

    @Test
    void getByMail_existingEmail_expectNonNullCustomer() {
        //Arrange
        String email = "mi@gmail.com";
        when(customerRepositoryMock.findCustomerByEmail(email)).thenReturn(Optional.of(new Customer()));
        //Act
        Customer customer = customerService.findCustomerByEmail(email);
        //Assert
        assertNotNull(customer);
    }

    @Test
    void getByMail_nullEmail_expectNonNullCustomer() {
        //Arrange
        when(customerRepositoryMock.findCustomerByEmail(null)).thenThrow(new NullPointerException("Null customer email is provided"));
        //Act
        assertThrows(NullPointerException.class, () -> customerService.findCustomerByEmail(null));
    }

    @Test
    void getByMail_nonExistingEmail_expectNonNullCustomer() {
        //Arrange
        String email = "fasarwas@gmail.com";
        when(customerRepositoryMock.findCustomerByEmail(anyString())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> customerService.findCustomerByEmail(email));
    }

    @Test
    void getByUsername_existingUsername_expectNonNullCustomer() {
        //Arrange
        String username = "mi";
        when(customerRepositoryMock.findCustomerByUserName(any(String.class))).thenReturn(Optional.of(new Customer()));
        //Act
        Customer customer = customerService.findCustomerByUserName(username);
        //Assert
        assertNotNull(customer);
    }

    @Test
    void getByUsername_nullUsername_expectNonNullCustomer() {
        //Arrange
        when(customerRepositoryMock.findCustomerByUserName(anyString())).thenThrow(new NullPointerException("Null customer username is provided"));
        //Act
        assertThrows(NullPointerException.class, () -> customerService.findCustomerByUserName(null));
    }

    @Test
    void getByUsername_nonExistingCustomer_expectNonNullCustomer() {
        //Arrange
        String username = "null username";
        when(customerRepositoryMock.findCustomerByUserName(anyString())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> customerService.findCustomerByUserName(username));
    }

    @Test
    void getAllCustomers_expectNonEmptyList() {
        //Arrange
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());
        when(customerRepositoryMock.findAll()).thenReturn(customers);
        //Act
        List<Customer> actualCustomerList = customerService.getAll();
        //Assert
        assertNotNull(actualCustomerList);
        assertEquals(2, actualCustomerList.size());
    }

    @Test
    void getAllCustomers_fetchErrorOccurred_expectHibernateException() {
        //Arrange
        when(customerRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        //Act
        assertThrows(HibernateException.class, customerService::getAll);
    }

    @Test
    void resetPassword_nonNullEmailAndPassword_expectTrue() {
        //Arrange
        String email = "mi@gmail.com";
        String password = "12345678";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        Optional<Customer> customerOptional = Optional.of(customer);
        when(customerRepositoryMock.findCustomerByEmail(anyString())).thenReturn(customerOptional);
        when(customerRepositoryMock.resetPassword(customerOptional.get(), password)).thenReturn(true);
        //Act
        boolean isPasswordChanged = customerService.resetPassword(email, password);
        //Assert
        assertTrue(isPasswordChanged);
    }

    @Test
    void resetPassword_nullEmailAndNonNullPassword_expectException() {
        //Arrange
        String password = "12345678";
        //Act
        assertThrows(NullPointerException.class, () -> customerService.resetPassword(null, password));
    }

    @Test
    void resetPassword_nonNullEmailAndNullPassword_expectException() {
        //Arrange
        String email = "mi@gmail.com";
        Optional<Customer> customer = Optional.of(new Customer());
        when(customerRepositoryMock.findCustomerByEmail(email)).thenReturn(customer);
        when(customerRepositoryMock.resetPassword(customer.get(), null)).thenThrow(new NullPointerException("Null customer password is provided"));
        //Act
        assertThrows(NullPointerException.class, () -> customerService.resetPassword(email, null));
    }

    @Test
    void resetPassword_notExistingCustomer_expectException() {
        //Arrange
        String email = "mi@gmail.com";
        String password = "12345678";
        Optional<Customer> customer = Optional.empty();
        when(customerRepositoryMock.findCustomerByEmail(email)).thenReturn(customer);
        when(customerRepositoryMock.resetPassword(null, password)).thenThrow(new NullCustomerException("Null customer password is provided"));
        //Act
        assertThrows(HibernateException.class, () -> customerService.resetPassword(email, password));
    }

    @Test
    void expireOtp_nonNullUsername_expectTrue() {
        //Arrange
        String username = "mi";
        Optional<Customer> customer = Optional.of(new Customer());
        when(customerRepositoryMock.findCustomerByUserName(username)).thenReturn(customer);
        when(customerRepositoryMock.expireOtp(customer.get())).thenReturn(true);
        //Act
        boolean isCodeReset = customerService.expireOtp(username);
        //Assert
        assertTrue(isCodeReset);
    }

    @Test
    void expireOtp_nullUsername_expectNullPointerException() {
        //Arrange
        String username = null;
        //Act
        assertThrows(NullPointerException.class, () -> customerService.expireOtp(username));
    }

    @Test
    void expireOtp_nullCustomer_expectNullCustomerException() {
        //Arrange
        String username = "mi";
        Optional<Customer> customer = Optional.empty();
        when(customerRepositoryMock.findCustomerByUserName(anyString())).thenReturn(customer);
        when(customerRepositoryMock.expireOtp(null)).thenThrow(new NullCustomerException("Customer not found with username provided"));
        //Act
        assertThrows(HibernateException.class, () -> customerService.expireOtp(username));
    }

     */
}
