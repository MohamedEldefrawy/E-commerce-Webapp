//package service;
//
//import com.vodafone.Ecommerce.exception.NullIdException;
//import com.vodafone.Ecommerce.exception.customer.IncompleteUserAttributesException;
//import com.vodafone.Ecommerce.exception.customer.NullCustomerException;
//import com.vodafone.Ecommerce.model.Customer;
//import com.vodafone.Ecommerce.model.Role;
//import com.vodafone.Ecommerce.model.UserStatus;
//import com.vodafone.Ecommerce.repository.customer.CustomerRepository;
//import com.vodafone.Ecommerce.repository.customer.ICustomerRepository;
//import com.vodafone.Ecommerce.service.CustomerService;
//import com.vodafone.Ecommerce.service.HashService;
//import org.hibernate.HibernateException;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class CustomerServiceUnitTest {
//    private static final ICustomerRepository customerRepositoryMock = mock(CustomerRepository.class);
//    private static final HashService hashService = mock(HashService.class);
//    private static final CustomerService customerService = new CustomerService(customerRepositoryMock, hashService);
//    private final Logger logger = LoggerFactory.getLogger(CustomerServiceUnitTest.class);
//
//    @Test
//    void createCustomer_nonNullEntity_expectTrue() {
//        //Arrange
//        Customer customer = new Customer();
//        customer.setUserName("Mohammed Yasser");
//        customer.setEmail("mohammedre4a@gmail.com");
//        customer.setPassword("12345678");
//        customer.setRole(Role.Customer);
//        customer.setUserStatus(UserStatus.ACTIVATED);
//        when(customerRepositoryMock.create(customer)).thenReturn(Optional.of(1L));
//        //Act
//        boolean isCustomerCreatedSuccessfully = customerService.create(customer);
//        //Assert
//        assertTrue(isCustomerCreatedSuccessfully);
//    }
//
//    @Test
//    void createCustomer_NullEntity_expectNullCustomerException() {
//        //Arrange
//        when(customerRepositoryMock.create(null)).thenThrow(new NullCustomerException("Null customer entity is provided"));
//        //Act
//        assertThrows(NullCustomerException.class, () -> customerService.create(null));
//    }
//
//    @Test
//    void createCustomer_NullEntity_expectIncompleteUserAttributesException() {
//        //Arrange
//        Customer customer = new Customer();
//        customer.setUserName("Mohammed Yasser");
//        customer.setEmail("mohammedre4a@gmail.com");
//        customer.setRole(Role.Customer);
//        customer.setUserStatus(UserStatus.ACTIVATED);
//        when(customerRepositoryMock.create(customer)).thenThrow(new IncompleteUserAttributesException("Customer Data is not completed"));
//        //Act
//        assertThrows(IncompleteUserAttributesException.class, () -> customerService.create(customer));
//    }
//
//    @Test
//    void updateCustomer_nonNullEntity_expectTrue() {
//        //Arrange
//        Customer customer = new Customer();
//        customer.setUserName("Mohammed Yasser");
//        customer.setEmail("mohammedre4a@gmail.com");
//        customer.setPassword("12345678");
//        customer.setRole(Role.Customer);
//        customer.setUserStatus(UserStatus.ACTIVATED);
//        when(customerRepositoryMock.getById(1L)).thenReturn(Optional.of(new Customer()));
//        when(customerRepositoryMock.update(1L, customer)).thenReturn(true);
//        //Act
//        boolean isCustomerUpdatedSuccessfully = customerService.update(1L, customer);
//        //Assert
//        assertTrue(isCustomerUpdatedSuccessfully);
//    }
//
//    @Test
//    void updateCustomer_NullEntity_expectNullCustomerException() {
//        //Arrange
//
//        when(customerRepositoryMock.getById(1L)).thenReturn(Optional.of(new Customer()));
//        when(customerRepositoryMock.update(1L, null)).thenThrow(new NullCustomerException("Null customer entity is provided"));
//        //Act
//        assertThrows(NullCustomerException.class, () -> customerService.update(1L, null));
//    }
//
//    @Test
//    void updateCustomer_NullId_expectNullCustomerException() {
//        //Arrange
//        Customer customer = new Customer();
//        customer.setUserName("Mohammed Yasser");
//        customer.setEmail("mohammedre4a@gmail.com");
//        customer.setPassword("12345678");
//        customer.setRole(Role.Customer);
//        customer.setUserStatus(UserStatus.ACTIVATED);
//        when(customerRepositoryMock.update(null, customer)).thenThrow(new NullIdException("Null customer id is provided"));
//        //Act
//        assertThrows(NullIdException.class, () -> customerService.update(null, customer));
//    }
//
//    @Test
//    void updateCustomer_notExistingCustomer_expectHibernateException() {
//        //Arrange
//        when(customerRepositoryMock.getById(anyLong())).thenReturn(Optional.empty());
//        //Act
//        assertThrows(HibernateException.class, () -> customerService.update(anyLong(), new Customer()));
//    }
//
//    @Test
//    void updateCustomerStatusToActivated_existCustomerEmail_expectTrue() {
//        Customer customer = new Customer();
//        String email = "mohammedre4a@gmail.com";
//        customer.setEmail(email);
//        when(customerRepositoryMock.getByMail(email)).thenReturn(Optional.of(customer));
//        when(customerRepositoryMock.updateStatusActivated(customer)).thenReturn(true);
//        //Act
//        boolean isCustomerActivated = customerService.updateStatusActivated(customer.getEmail());
//        //Assert
//        assertTrue(isCustomerActivated);
//    }
//
//    @Test
//    void updateCustomerStatusToActivated_nullCustomerEmail_expectNullCustomerException() {
//        when(customerRepositoryMock.updateStatusActivated(null)).thenThrow(new NullPointerException("Null email is provided"));
//        when(customerRepositoryMock.getByMail("mohammedre4a@gmail.com")).thenReturn(Optional.of(new Customer()));
//        //Act
//        assertThrows(NullPointerException.class, () -> customerService.updateStatusActivated(null));
//    }
//
//    @Test
//    void updateCustomerStatusToActivated_nullEmail_expectNullPointerException() {
//        when(customerRepositoryMock.updateStatusActivated(new Customer())).thenThrow(new NullPointerException("Null email is provided"));
//        //Act
//        assertThrows(NullPointerException.class, () -> customerService.updateStatusActivated(null));
//    }
//
//    @Test
//    void updateCustomerStatusToActivated_notExistingCustomer_expectHibernateException() {
//        //Arrange
//        when(customerRepositoryMock.getByMail(anyString())).thenReturn(Optional.empty());
//        //Act
//        assertThrows(HibernateException.class, () -> customerService.updateStatusActivated(anyString()));
//    }
//
//    @Test
//    void deleteCustomer_nonNullEntity_expectTrue() {
//        //Arrange
//        when(customerRepositoryMock.delete(1L)).thenReturn(true);
//        when(customerRepositoryMock.getById(1L)).thenReturn(Optional.of(new Customer()));
//
//        //Act
//        boolean isCustomerUpdatedSuccessfully = customerService.delete(1L);
//        //Assert
//        assertTrue(isCustomerUpdatedSuccessfully);
//    }
//
//    @Test
//    void deleteCustomer_NullId_expectNullIdException() {
//        //Arrange
//        when(customerRepositoryMock.delete(null)).thenThrow(new NullIdException("Null customer id is provided"));
//        when(customerRepositoryMock.getById(1L)).thenReturn(Optional.of(new Customer()));
//
//        //Act
//        assertThrows(NullIdException.class, () -> customerService.delete(null));
//    }
//
//    @Test
//    void deleteCustomer_notExistingCustomer_expectHibernateException() {
//        //Arrange
//        when(customerRepositoryMock.getById(anyLong())).thenReturn(Optional.empty());
//        //Act
//        assertThrows(HibernateException.class, () -> customerService.delete(anyLong()));
//    }
//
//    @Test
//    void getCustomer_nonNullId_expectNonNullCustomerObject() {
//        //Arrange
//        Customer customer = new Customer();
//        customer.setUserName("Mohammed Yasser");
//        customer.setEmail("mohammedre4a@gmail.com");
//        customer.setPassword("12345678");
//        customer.setRole(Role.Customer);
//        customer.setUserStatus(UserStatus.ACTIVATED);
//        when(customerRepositoryMock.getById(1L)).thenReturn(Optional.of(customer));
//        //Act
//        Customer customerObj = customerService.getById(1L);
//        //Assert
//        assertNotNull(customerObj);
//    }
//
//    @Test
//    void getCustomer_NullId_expectNullIdException() {
//        //Arrange
//        when(customerRepositoryMock.getById(null)).thenThrow(new NullIdException("Null customer id is provided"));
//        //Act
//        assertThrows(NullIdException.class, () -> customerService.getById(null));
//    }
//
//    @Test
//    void getCustomer_notExistingCustomer_expectHibernateException() {
//        //Arrange
//        when(customerRepositoryMock.getById(anyLong())).thenReturn(Optional.empty());
//        //Act
//        assertThrows(HibernateException.class, () -> customerService.getById(anyLong()));
//    }
//
//    @Test
//    void getByMail_existingEmail_expectNonNullCustomer() {
//        //Arrange
//        String email = "mi@gmail.com";
//        when(customerRepositoryMock.getByMail(email)).thenReturn(Optional.of(new Customer()));
//        //Act
//        Customer customer = customerService.getByMail(email);
//        //Assert
//        assertNotNull(customer);
//    }
//
//    @Test
//    void getByMail_nullEmail_expectNonNullCustomer() {
//        //Arrange
//        when(customerRepositoryMock.getByMail(null)).thenThrow(new NullPointerException("Null customer email is provided"));
//        //Act
//        assertThrows(NullPointerException.class, () -> customerService.getByMail(null));
//    }
//
//    @Test
//    void getByMail_nonExistingEmail_expectNonNullCustomer() {
//        //Arrange
//        String email = "fasarwas@gmail.com";
//        when(customerRepositoryMock.getByMail(anyString())).thenReturn(Optional.empty());
//        //Act
//        assertThrows(HibernateException.class, () -> customerService.getByMail(email));
//    }
//
//    @Test
//    void getByUsername_existingUsername_expectNonNullCustomer() {
//        //Arrange
//        String username = "mi";
//        when(customerRepositoryMock.getByUserName(any(String.class))).thenReturn(Optional.of(new Customer()));
//        //Act
//        Customer customer = customerService.getByUserName(username);
//        //Assert
//        assertNotNull(customer);
//    }
//
//    @Test
//    void getByUsername_nullUsername_expectNonNullCustomer() {
//        //Arrange
//        when(customerRepositoryMock.getByUserName(anyString())).thenThrow(new NullPointerException("Null customer username is provided"));
//        //Act
//        assertThrows(NullPointerException.class, () -> customerService.getByUserName(null));
//    }
//
//    @Test
//    void getByUsername_nonExistingCustomer_expectNonNullCustomer() {
//        //Arrange
//        String username = "null username";
//        when(customerRepositoryMock.getByUserName(anyString())).thenReturn(Optional.empty());
//        //Act
//        assertThrows(HibernateException.class, () -> customerService.getByUserName(username));
//    }
//
//    @Test
//    void getAllCustomers_expectNonEmptyList(){
//        //Arrange
//        List<Customer> customers = new ArrayList<>();
//        customers.add(new Customer());
//        customers.add(new Customer());
//        when(customerRepositoryMock.getAll()).thenReturn(Optional.of(customers));
//        //Act
//        List<Customer> actualCustomerList = customerService.getAll();
//        //Assert
//        assertNotNull(actualCustomerList);
//        assertEquals(2,actualCustomerList.size());
//    }
//
//    @Test
//    void getAllCustomers_fetchErrorOccurred_expectHibernateException(){
//        //Arrange
//        when(customerRepositoryMock.getAll()).thenReturn(Optional.empty());
//        //Act
//        assertThrows(HibernateException.class, customerService::getAll);
//    }
//
//    @Test
//    void resetPassword_nonNullEmailAndPassword_expectTrue() {
//        //Arrange
//        String email = "mi@gmail.com";
//        String password = "12345678";
//        Customer customer = new Customer();
//        customer.setEmail(email);
//        customer.setPassword(password);
//        Optional<Customer> customerOptional = Optional.of(customer);
//        when(customerRepositoryMock.getByMail(anyString())).thenReturn(customerOptional);
//        when(customerRepositoryMock.resetPassword(customerOptional.get(), password)).thenReturn(true);
//        //Act
//        boolean isPasswordChanged = customerService.resetPassword(email, password);
//        //Assert
//        assertTrue(isPasswordChanged);
//    }
//
//    @Test
//    void resetPassword_nullEmailAndNonNullPassword_expectException() {
//        //Arrange
//        String password = "12345678";
//        //Act
//        assertThrows(NullPointerException.class, () -> customerService.resetPassword(null, password));
//    }
//
//    @Test
//    void resetPassword_nonNullEmailAndNullPassword_expectException() {
//        //Arrange
//        String email = "mi@gmail.com";
//        Optional<Customer> customer = Optional.of(new Customer());
//        when(customerRepositoryMock.getByMail(email)).thenReturn(customer);
//        when(customerRepositoryMock.resetPassword(customer.get(), null)).thenThrow(new NullPointerException("Null customer password is provided"));
//        //Act
//        assertThrows(NullPointerException.class, () -> customerService.resetPassword(email, null));
//    }
//
//    @Test
//    void resetPassword_notExistingCustomer_expectException() {
//        //Arrange
//        String email = "mi@gmail.com";
//        String password = "12345678";
//        Optional<Customer> customer = Optional.empty();
//        when(customerRepositoryMock.getByMail(email)).thenReturn(customer);
//        when(customerRepositoryMock.resetPassword(null, password)).thenThrow(new NullCustomerException("Null customer password is provided"));
//        //Act
//        assertThrows(HibernateException.class, () -> customerService.resetPassword(email, password));
//    }
//
//    @Test
//    void expireOtp_nonNullUsername_expectTrue() {
//        //Arrange
//        String username = "mi";
//        Optional<Customer> customer = Optional.of(new Customer());
//        when(customerRepositoryMock.getByUserName(username)).thenReturn(customer);
//        when(customerRepositoryMock.expireOtp(customer.get())).thenReturn(true);
//        //Act
//        boolean isCodeReset = customerService.expireOtp(username);
//        //Assert
//        assertTrue(isCodeReset);
//    }
//
//    @Test
//    void expireOtp_nullUsername_expectNullPointerException() {
//        //Arrange
//        String username = null;
//        //Act
//        assertThrows(NullPointerException.class, () -> customerService.expireOtp(username));
//    }
//
//    @Test
//    void expireOtp_nullCustomer_expectNullCustomerException() {
//        //Arrange
//        String username = "mi";
//        Optional<Customer> customer = Optional.empty();
//        when(customerRepositoryMock.getByUserName(anyString())).thenReturn(customer);
//        when(customerRepositoryMock.expireOtp(null)).thenThrow(new NullCustomerException("Customer not found with username provided"));
//        //Act
//        assertThrows(HibernateException.class, () -> customerService.expireOtp(username));
//    }
//}
