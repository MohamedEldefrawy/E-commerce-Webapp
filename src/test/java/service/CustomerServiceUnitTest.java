package service;

import com.vodafone.exception.IncompleteUserAttributesException;
import com.vodafone.exception.NullCustomerException;
import com.vodafone.model.Customer;
import com.vodafone.model.Role;
import com.vodafone.model.UserStatus;
import com.vodafone.repository.customer.CustomerRepository;
import com.vodafone.repository.customer.ICustomerRepository;
import com.vodafone.service.CustomerService;
import com.vodafone.service.HashService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerServiceUnitTest {
    private static final ICustomerRepository customerRepositoryMock = mock(CustomerRepository.class);
    private static final HashService hashService = mock(HashService.class);
    private static final CustomerService customerService = new CustomerService(customerRepositoryMock, hashService);

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


}
