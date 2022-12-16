package controller;

import cn.org.rapid_framework.web.session.wrapper.HttpSessionWrapper;
import com.vodafone.controller.CustomerController;
import com.vodafone.exception.product.GetProductException;
import com.vodafone.model.Customer;
import com.vodafone.model.Product;
import com.vodafone.model.dto.ResetPasswordDTO;
import com.vodafone.service.*;
import com.vodafone.validators.CustomerValidator;
import com.vodafone.validators.UserAuthorizer;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerControllerUnitTest {
    private final CustomerService customerService = mock(CustomerService.class);
    private final OrderService orderService = mock(OrderService.class);
    private final ProductService productService = mock(ProductService.class);
    private final CartService cartService = mock(CartService.class);
    private final SendEmailService sendEmailService = mock(SendEmailService.class);
    private final UserAuthorizer userAuthorizer = mock(UserAuthorizer.class);
    private final CustomerValidator customerValidator = mock(CustomerValidator.class);
    private final HashService hashService = mock(HashService.class);
    private final CustomerController customerController = new CustomerController(customerService, orderService, productService, cartService, sendEmailService, userAuthorizer, customerValidator, hashService);

    private final BindingResult bindingResult = mock(BindingResult.class);

    private final HttpSession session = mock(HttpSession.class);
    private final Model model = new ConcurrentModel();


    @Test
    void customerHomeNavigation_sendSessionAndModelAndAuthorized_expectHomeViewString() {
        //Arrange
        //User is authorized
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(productService.getByCategory(anyString())).thenReturn(new ArrayList<>());
        when(productService.getAvailableProducts()).thenReturn(new ArrayList<>());

        //Act
        String viewName = customerController.home(session, model, "Cats");
        //Assert
        assertNotNull(viewName);
        assertEquals("/customer/shared/home", viewName);
    }

    @Test
    void customerHomeNavigation_sendSessionAndModelAndAuthorized_expectExceptionAndHomeViewString() {
        //Arrange
        //User is authorized
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(productService.getByCategory(anyString())).thenThrow(new GetProductException("Error occurred while fetching products"));
        when(productService.getAvailableProducts()).thenReturn(new ArrayList<>());

        //Act
        String viewName = customerController.home(session, model, "Cats");
        //Assert
        assertNotNull(model.getAttribute("products"));
        assertEquals("/customer/shared/home", viewName);
    }

    @Test
    void customerHomeNavigation_nullCategoryAndAuthorized_expectExceptionAndHomeViewString() {
        //Arrange
        //User is authorized
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(productService.getByCategory(anyString())).thenThrow(new GetProductException("Error occurred while fetching products"));
        when(productService.getAvailableProducts()).thenReturn(new ArrayList<>());

        //Act
        String viewName = customerController.home(session, model, null);
        //Assert
        assertNotNull(model.getAttribute("products"));
        assertEquals("/customer/shared/home", viewName);
    }

    @Test
    void customerHomeNavigation_sendSessionAndModelAndNotAuthorizedCustomer_expectLoginViewString() {
        //Arrange
        //User is not authorized
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(false);

        //Act
        String viewName = customerController.home(session, model, "Cats");
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm", viewName);
    }

    @Test
    void searchByProductName_sendSessionAndModelAndCategoryAndName_expectHomeViewString() {
        //Arrange
        //User is authorized
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(productService.getByCategory(anyString())).thenReturn(new ArrayList<>());
        when(productService.getByName(anyString())).thenReturn(new Product());

        String category = "Cats";
        String name = "Meow";
        //Act
        String viewName = customerController.searchByProductName(session, model, category, name);
        //Assert
        assertNotNull(viewName);
        assertEquals("/customer/shared/home", viewName);
    }

    @Test
    void searchByProductName_sendSessionAndModelAndCategoryAndName_expectCategoryGetProductExceptionAndHomeViewString() {
        //Arrange
        //User is authorized
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(productService.getByCategory(anyString())).thenThrow(new GetProductException("Error occurred while fetching category's products"));

        String category = "Cats";
        String name = "Meow";
        //Act
        String viewName = customerController.searchByProductName(session, model, category, name);
        //Assert
        assertNotNull(viewName);
        assertEquals("/customer/shared/home", viewName);
    }

    @Test
    void searchByProductName_sendSessionAndModelAndCategoryAndName_expectProductNameGetProductExceptionAndHomeViewString() {
        //Arrange
        //User is authorized
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(productService.getByName(anyString())).thenThrow(new GetProductException("Error occurred while fetching category's products"));

        String category = "Cats";
        String name = "Meow";
        //Act
        String viewName = customerController.searchByProductName(session, model, category, name);
        //Assert
        assertNotNull(viewName);
        assertEquals("/customer/shared/home", viewName);
    }

    @Test
    void searchByProductName_unauthorizedUser_expectLoginViewString() {
        //Arrange
        //User is authorized
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(false);

        String category = "Cats";
        String name = "Meow";
        //Act
        String viewName = customerController.searchByProductName(session, model, category, name);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm", viewName);
    }

    @Test
    void getAllCustomers_expectNotEmptyList() {
        //Arrange
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        when(customerService.getAll()).thenReturn(customers);
        //Act
        List<Customer> actual = customerController.getAll();
        //Assert
        assertNotNull(actual);
        assertTrue(actual.size() > 0);
    }

    @Test
    void getAllCustomers_expectHibernateException() {
        //Arrange
        when(customerService.getAll()).thenThrow(HibernateException.class);
        //Act
        assertThrows(HibernateException.class, () -> customerController.getAll());
    }

    @Test
    void resetPasswordGet_sessionAndModelAndExistingCustomer_expectResetPasswordViewString() {
        //Arrange
        when(userAuthorizer.customerExists(session)).thenReturn(true);
        //Act
        String viewName = customerController.resetPasswordLoader(session, model);
        //Assert
        assertNotNull(viewName);
        assertEquals("resetPassword", viewName);
    }

    @Test
    void resetPasswordGet_sessionAndModelAndNotExistingCustomer_expectResetPasswordViewString() {
        //Arrange
        when(userAuthorizer.customerExists(session)).thenReturn(false);
        //Act
        String viewName = customerController.resetPasswordLoader(session, model);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm", viewName);
    }

    @Test
    void resetPasswordPost_existUserAndResetPassword_expectLoginViewString() {
        //Arrange
        String email = "mohammedre4a@gmail.com";
        String password = "12345678";
        when(userAuthorizer.customerExists(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(session.getAttribute("email")).thenReturn(email);
        when(hashService.encryptPassword(anyString(),anyString())).thenReturn(password);
        when(customerService.resetPassword(email, password)).thenReturn(true);
        //Act
        String viewName = customerController.resetPassword(new ResetPasswordDTO(password), bindingResult, session);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm", viewName);
    }
    @Test
    void resetPasswordPost_existUserAndResetPasswordFalse_expectResetPasswordViewString() {
        //Arrange
        String email = "mohammedre4a@gmail.com";
        String password = "12345678";
        when(userAuthorizer.customerExists(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(session.getAttribute("email")).thenReturn(email);
        when(hashService.encryptPassword(anyString(),anyString())).thenReturn(password);
        when(customerService.resetPassword(email, password)).thenReturn(false);
        //Act
        String viewName = customerController.resetPassword(new ResetPasswordDTO(password), bindingResult, session);
        //Assert
        assertNotNull(viewName);
        assertEquals("resetPassword", viewName);
    }
    @Test
    void resetPasswordPost_notExistingUser_expectLoginViewString() {
        //Arrange
        String password = "12345678";
        when(userAuthorizer.customerExists(any(HttpSession.class))).thenReturn(false);
        //Act
        String viewName = customerController.resetPassword(new ResetPasswordDTO(password), bindingResult, session);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm", viewName);
    }
    @Test
    void resetPasswordPost_bindingResultErrors_expectRegistrationViewString() {
        //Arrange
        when(userAuthorizer.customerExists(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);
        //Act
        String viewName = customerController.resetPassword(new ResetPasswordDTO(""), bindingResult, session);
        //Assert
        assertNotNull(viewName);
        assertEquals("registration", viewName);
    }

    @Test
    void viewProductDetails_customerIsActivatedAndProductExists_expectDetailViewString(){
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(productService.getById(anyLong())).thenReturn(new Product());
        //Act
        String viewName = customerController.viewProductDetails(session,model,1L);
        //Assert
        assertNotNull(viewName);
        assertEquals("/customer/product/detail",viewName);
    }
    //todo: update function to expect error page
    @Test
    void viewProductDetails_customerIsActivatedAndProductNotExists_expectDetailViewString(){
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(productService.getById(anyLong())).thenThrow(new GetProductException("Error occurred while fetching product by id"));
        //Act
        String viewName = customerController.viewProductDetails(session,model,1L);
        //Assert
        assertNotNull(viewName);
        assertEquals("/customer/product/detail",viewName);
    }

    @Test
    void viewProductDetails_customerIsNotActivated_expectLoginViewString(){
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(false);
        //Act
        String viewName = customerController.viewProductDetails(session,model,1L);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm",viewName);
    }

    @Test
    void getCustomerOrders_customerIsActivated_expectOrdersViewString(){
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(orderService.getByCustomerId(anyLong())).thenReturn(new ArrayList<>());
        //Act
        String viewName = customerController.getCustomerOrders(session,model);
        //Assert
        assertNotNull(viewName);
        assertEquals("/customer/shared/orders",viewName);
    }
    @Test
    void getCustomerOrders_customerIsNotActivated_expectLoginViewString(){
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(false);
        //Act
        String viewName = customerController.getCustomerOrders(session,model);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm",viewName);
    }


}
