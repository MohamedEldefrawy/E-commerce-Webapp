package controller;

import com.vodafone.controller.CustomerController;
import com.vodafone.exception.product.GetProductException;
import com.vodafone.model.*;
import com.vodafone.model.dto.ResetPasswordDTO;
import com.vodafone.service.*;
import com.vodafone.validators.CustomerValidator;
import com.vodafone.validators.UserAuthorizer;
import org.hibernate.HibernateException;
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
        when(hashService.encryptPassword(anyString(), anyString())).thenReturn(password);
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
        when(hashService.encryptPassword(anyString(), anyString())).thenReturn(password);
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
    void viewProductDetails_customerIsActivatedAndProductExists_expectDetailViewString() {
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(productService.getById(anyLong())).thenReturn(new Product());
        //Act
        String viewName = customerController.viewProductDetails(session, model, 1L);
        //Assert
        assertNotNull(viewName);
        assertEquals("/customer/product/detail", viewName);
    }

    @Test
    void viewProductDetails_customerIsActivatedAndProductNotExists_expectErrorPageViewString() {
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(productService.getById(anyLong())).thenThrow(new GetProductException("Error occurred while fetching product by id"));
        //Act
        String viewName = customerController.viewProductDetails(session, model, 1L);
        //Assert
        assertNotNull(viewName);
        assertEquals("/customer/shared/error404", viewName);
    }

    @Test
    void viewProductDetails_customerIsNotActivated_expectLoginViewString() {
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(false);
        //Act
        String viewName = customerController.viewProductDetails(session, model, 1L);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm", viewName);
    }

    @Test
    void getCustomerOrders_customerIsActivated_expectOrdersViewString() {
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(orderService.getByCustomerId(anyLong())).thenReturn(new ArrayList<>());
        //Act
        String viewName = customerController.getCustomerOrders(session, model);
        //Assert
        assertNotNull(viewName);
        assertEquals("/customer/shared/orders", viewName);
    }

    @Test
    void getCustomerOrders_customerIsNotActivated_expectLoginViewString() {
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(false);
        //Act
        String viewName = customerController.getCustomerOrders(session, model);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm", viewName);
    }

    @Test
    void submitFinalOrder_customerIsActivatedAndOrderIsCreated_expect200() {
        //Arrange
        Customer customer = new Customer();
        customer.setCart(new Cart(1L, customer, new ArrayList<>()));
        Order order = new Order();
        order.setCustomer(customer);
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(1L)).thenReturn(customer);
        when(cartService.submitFinalOrder(1L)).thenReturn(order);
        when(orderService.create(any(Order.class))).thenReturn(true);
        //Act
        String responseBodyString = customerController.submitFinalOrder(session);
        //Assert
        assertNotNull(responseBodyString);
        assertEquals("200", responseBodyString);
    }

    @Test
    void submitFinalOrder_customerIsActivatedAndOrderIsNotCreated_expect500() {
        //Arrange
        Customer customer = new Customer();
        customer.setCart(new Cart(1L, customer, new ArrayList<>()));
        Order order = new Order();
        order.setCustomer(customer);
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(1L)).thenReturn(customer);
        when(cartService.submitFinalOrder(anyLong())).thenReturn(order);
        when(orderService.create(any(Order.class))).thenReturn(false);
        //Act
        String responseBodyString = customerController.submitFinalOrder(session);
        //Assert
        assertNotNull(responseBodyString);
        assertEquals("500", responseBodyString);
    }

    @Test
    void submitFinalOrder_customerIsNotActivated_expect401() {
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(false);
        //Act
        String responseBodyString = customerController.submitFinalOrder(session);
        //Assert
        assertNotNull(responseBodyString);
        assertEquals("401", responseBodyString);
    }

    @Test
    void showCustomerCart_customerIsActivated_expectCartViewString() {
        //Arrange
        Customer customer = new Customer();
        customer.setCart(new Cart(1L, customer, new ArrayList<>()));
        Order order = new Order();
        order.setCustomer(customer);
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(anyLong())).thenReturn(customer);
        //Act
        String viewName = customerController.showCustomerCart(model, session);
        //Assert
        assertNotNull(viewName);
        assertEquals("/customer/shared/cart", viewName);
    }

    @Test
    void showCustomerCart_customerIsNotActivated_expectLoginViewString() {
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(false);
        //Act
        String viewName = customerController.showCustomerCart(model, session);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm", viewName);
    }

    //product fetching error -> return error404
    @Test
    void addItemToCart_customerIsActivatedAndProductExistAndPositiveQuantity_expect200() {
        //Arrange
        Customer customer = new Customer();
        customer.setCart(new Cart(1L, customer, new ArrayList<>()));
        Order order = new Order();
        order.setCustomer(customer);
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(anyLong())).thenReturn(customer);
        when(productService.getById(anyLong())).thenReturn(new Product());
        when(cartService.addItem(anyLong(), any(CartItem.class))).thenReturn(5);
        //Act
        String responseBody = customerController.addItemToCart(session, 1L, 5);
        //Assert
        assertNotNull(responseBody);
        assertEquals("200", responseBody);
    }

    @Test
    void addItemToCart_customerIsActivatedAndProductNotExist_expect404Error() {
        //Arrange
        Customer customer = new Customer();
        customer.setCart(new Cart(1L, customer, new ArrayList<>()));
        Order order = new Order();
        order.setCustomer(customer);
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(anyLong())).thenReturn(customer);
        when(productService.getById(anyLong())).thenThrow(new GetProductException("Error occurred while fetching product by id"));
        //Act
        String viewName = customerController.addItemToCart(session, 1L, 5);
        //Assert
        assertNotNull(viewName);
        assertEquals("/customer/shared/error404", viewName);
    }

    @Test
    void addItemToCart_customerIsActivatedAndProductExistAndNegativeQuantity_expect500() {
        //Arrange
        Customer customer = new Customer();
        customer.setCart(new Cart(1L, customer, new ArrayList<>()));
        Order order = new Order();
        order.setCustomer(customer);
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(anyLong())).thenReturn(customer);
        when(productService.getById(anyLong())).thenReturn(new Product());
        when(cartService.addItem(anyLong(), any(CartItem.class))).thenReturn(-1);
        //Act
        String responseBody = customerController.addItemToCart(session, 1L, -1);
        //Assert
        assertNotNull(responseBody);
        assertEquals("500", responseBody);
    }

    @Test
    void addItemToCart_customerIsActivatedAndProductExistAndZeroQuantity_expect409() {
        //Arrange
        Customer customer = new Customer();
        customer.setCart(new Cart(1L, customer, new ArrayList<>()));
        Order order = new Order();
        order.setCustomer(customer);
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(anyLong())).thenReturn(customer);
        when(productService.getById(anyLong())).thenReturn(new Product());
        when(cartService.addItem(anyLong(), any(CartItem.class))).thenReturn(0);
        //Act
        String responseBody = customerController.addItemToCart(session, 1L, 0);
        //Assert
        assertNotNull(responseBody);
        assertEquals("409", responseBody);
    }

    @Test
    void addItemToCart_customerIsNotActivated_expect401() {
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(false);
        //Act
        String responseBody = customerController.addItemToCart(session, 1L, 100);
        //Assert
        assertNotNull(responseBody);
        assertEquals("401", responseBody);
    }

    @Test
    void removeItemFromCart_customerIsActivatedAndItemIsRemoved_expect200() {
        //Arrange
        Customer customer = new Customer();
        customer.setCart(new Cart(1L, customer, new ArrayList<>()));
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(anyLong())).thenReturn(customer);
        when(cartService.removeItem(anyLong(), anyLong())).thenReturn(true);
        //Act
        String responseBody = customerController.removeItemFromCart(session, 1L);
        //Assert
        assertNotNull(responseBody);
        assertEquals("200", responseBody);
    }

    @Test
    void removeItemFromCart_customerIsActivatedAndItemIsNotRemoved_expect500() {
        //Arrange
        Customer customer = new Customer();
        customer.setCart(new Cart(1L, customer, new ArrayList<>()));
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(anyLong())).thenReturn(customer);
        when(cartService.removeItem(anyLong(), anyLong())).thenReturn(false);
        //Act
        String responseBody = customerController.removeItemFromCart(session, 1L);
        //Assert
        assertNotNull(responseBody);
        assertEquals("500", responseBody);
    }

    @Test
    void removeItemFromCart_customerIsNotActivated_expect401() {
        //Arrange
        when(userAuthorizer.isActivatedCustomer(any(HttpSession.class))).thenReturn(false);
        //Act
        String responseBody = customerController.removeItemFromCart(session, 1L);
        //Assert
        assertNotNull(responseBody);
        assertEquals("401", responseBody);
    }

    @Test
    void registrationGet_expectRegistrationViewString() {
        //Act
        String actualView = customerController.registration(model);
        //Assert
        assertNotNull(actualView);
        assertEquals("registration", actualView);
    }

    @Test
    void resendOtp_customerExistsAndEmailIsSent_expectVerifyViewString() {
        //Arrange
        Customer customer = new Customer();
        when(userAuthorizer.customerExists(any(HttpSession.class))).thenReturn(true);
        when(session.getAttribute("username")).thenReturn("mi");
        when(customerService.getByUserName(anyString())).thenReturn(customer);
        when(sendEmailService.getRandom()).thenReturn("153255");
        when(customerService.update(1L, customer)).thenReturn(true);
        when(sendEmailService.sendEmail(customer, EmailType.ACTIVATION, session)).thenReturn(true);
        //Act
        String viewName = customerController.resendOtp(session);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/customer/verify.htm", viewName);
    }

    @Test
    void resendOtp_customerExistsAndEmailIsNotSent_expectVerifyViewString() {
        //Arrange
        Customer customer = new Customer();
        when(userAuthorizer.customerExists(any(HttpSession.class))).thenReturn(true);
        when(session.getAttribute("username")).thenReturn("mi");
        when(customerService.getByUserName(anyString())).thenReturn(customer);
        when(sendEmailService.getRandom()).thenReturn("153255");
        when(customerService.update(1L, customer)).thenReturn(true);
        when(sendEmailService.sendEmail(customer, EmailType.ACTIVATION, session)).thenReturn(false);
        //Act
        String viewName = customerController.resendOtp(session);
        //Assert
        assertNotNull(viewName);
        assertEquals("registration", viewName);
    }

    @Test
    void resendOtp_customerNotExists_expectLoginViewString() {
        //Arrange
        when(userAuthorizer.customerExists(any(HttpSession.class))).thenReturn(false);
        //Act
        String viewName = customerController.resendOtp(session);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm", viewName);
    }

    @Test
    void verifyGet_authorizedUser_expectVerifyViewString() {
        //Arrange
        String username = "mohammed";
        when(userAuthorizer.customerExists(session)).thenReturn(true);
        when(customerService.expireOtp(anyString())).thenReturn(true);
        when(session.getAttribute("username")).thenReturn(username);
        //Act
        String viewName = customerController.verify(model, session);
        //Assert
        assertNotNull(viewName);
        assertEquals("verify", viewName);
    }

    @Test
    void verifyGet_unAuthorizedUser_expectLoginViewString() {
        //Arrange
        when(userAuthorizer.customerExists(session)).thenReturn(false);
        //Act
        String viewName = customerController.verify(model, session);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm", viewName);
    }

    @Test
    void verifyCustomer_existsAndRegisteredCustomer_expectHomeViewString() {
        //Arrange
        String code = "abc123";
        String email = "mohammedre4a@gmail.com";
        Customer customer = new Customer();
        customer.setCode(code);
        when(userAuthorizer.customerExists(session)).thenReturn(true);
        when(session.getAttribute("email")).thenReturn(email);
        when(customerService.getByMail(email)).thenReturn(customer);
        when(customerService.updateStatusActivated(email)).thenReturn(true);
        //Act
        String viewName = customerController.verifyCustomer(code, session, model);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/customer/home.htm", viewName);
    }

    @Test
    void verifyCustomer_existsAndNotRegisteredCustomer_expectRegistrationViewString() {
        //Arrange
        String code = "abc123";
        String email = "mohammedre4a@gmail.com";
        when(userAuthorizer.customerExists(session)).thenReturn(true);
        when(session.getAttribute("email")).thenReturn(email);
        when(customerService.getByMail(email)).thenReturn(null);
        //Act
        String viewName = customerController.verifyCustomer(code, session, model);
        //Assert
        assertNotNull(viewName);
        assertEquals("registration", viewName);
    }

    @Test
    void verifyCustomer_nullCode_expectVerifyViewString() {
        //Arrange
        String code = "abc123";
        String email = "mohammedre4a@gmail.com";
        when(userAuthorizer.customerExists(session)).thenReturn(true);
        when(session.getAttribute("email")).thenReturn(email);
        when(customerService.getByMail(email)).thenReturn(new Customer());
        when(customerService.updateStatusActivated(email)).thenReturn(true);
        //Act
        String viewName = customerController.verifyCustomer(code, session, model);
        //Assert
        assertNotNull(viewName);
        assertEquals("verify", viewName);
    }

    @Test
    void verifyCustomer_wrongCodeEntered_expectVerifyViewString() {
        //Arrange
        String code = "abc123";
        String email = "mohammedre4a@gmail.com";
        Customer customer = new Customer();
        customer.setCode(code);
        when(userAuthorizer.customerExists(session)).thenReturn(true);
        when(session.getAttribute("email")).thenReturn(email);
        when(customerService.getByMail(email)).thenReturn(customer);
        when(customerService.updateStatusActivated(email)).thenReturn(true);
        //Act
        String viewName = customerController.verifyCustomer("wrong_code", session, model);
        //Assert
        assertNotNull(viewName);
        assertEquals("verify", viewName);
    }

    @Test
    void verifyCustomer_notExistingCustomer_expectLoginViewString() {
        //Arrange
        when(userAuthorizer.customerExists(session)).thenReturn(false);
        //Act
        String viewName = customerController.verifyCustomer(anyString(), session, model);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm", viewName);
    }

    @Test
    void incrementProductQuantity_activatedCustomerAndPositiveQuantity_expect200() {
        //Arrange
        Customer customer = new Customer();
        Cart cart = new Cart(1L, customer, new ArrayList<>());
        customer.setCart(cart);
        when(userAuthorizer.isActivatedCustomer(session)).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(anyLong())).thenReturn(customer);
        when(cartService.incrementProductQuantity(anyLong(), anyLong(), anyInt())).thenReturn(10);
        //Act
        String responseBody = customerController.incrementProductQuantity(session, 1L);
        //Assert
        assertNotNull(responseBody);
        assertEquals("200", responseBody);
    }

    @Test
    void incrementProductQuantity_activatedCustomerAndNegativeQuantity_expect500() {
        //Arrange
        Customer customer = new Customer();
        Cart cart = new Cart(1L, customer, new ArrayList<>());
        customer.setCart(cart);
        when(userAuthorizer.isActivatedCustomer(session)).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(anyLong())).thenReturn(customer);
        when(cartService.incrementProductQuantity(anyLong(), anyLong(), anyInt())).thenReturn(-1);
        //Act
        String responseBody = customerController.incrementProductQuantity(session, 1L);
        //Assert
        assertNotNull(responseBody);
        assertEquals("500", responseBody);
    }

    @Test
    void incrementProductQuantity_activatedCustomerAndZeroQuantity_expect409() {
        //Arrange
        Customer customer = new Customer();
        Cart cart = new Cart(1L, customer, new ArrayList<>());
        customer.setCart(cart);
        when(userAuthorizer.isActivatedCustomer(session)).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(anyLong())).thenReturn(customer);
        when(cartService.incrementProductQuantity(anyLong(), anyLong(), anyInt())).thenReturn(0);
        //Act
        String responseBody = customerController.incrementProductQuantity(session, 1L);
        //Assert
        assertNotNull(responseBody);
        assertEquals("409", responseBody);
    }

    @Test
    void incrementProductQuantity_nonActivateCustomer_expect401() {
        //Arrange
        when(userAuthorizer.isActivatedCustomer(session)).thenReturn(false);
        //Act
        String responseBody = customerController.incrementProductQuantity(session, 1L);
        //Assert
        assertNotNull(responseBody);
        assertEquals("401", responseBody);
    }

    @Test
    void decrementProductQuantity_activatedCustomerAndPositiveQuantity_expect200() {
        //Arrange
        Customer customer = new Customer();
        Cart cart = new Cart(1L, customer, new ArrayList<>());
        customer.setCart(cart);
        when(userAuthorizer.isActivatedCustomer(session)).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(anyLong())).thenReturn(customer);
        when(cartService.decrementProductQuantity(anyLong(), anyLong(), anyInt())).thenReturn(10);
        //Act
        String responseBody = customerController.decrementProductQuantity(session, 1L);
        //Assert
        assertNotNull(responseBody);
        assertEquals("200", responseBody);
    }

    @Test
    void decrementProductQuantity_activatedCustomerAndNegativeQuantity_expect500() {
        //Arrange
        Customer customer = new Customer();
        Cart cart = new Cart(1L, customer, new ArrayList<>());
        customer.setCart(cart);
        when(userAuthorizer.isActivatedCustomer(session)).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(1L);
        when(customerService.getById(anyLong())).thenReturn(customer);
        when(cartService.decrementProductQuantity(anyLong(), anyLong(), anyInt())).thenReturn(-1);
        //Act
        String responseBody = customerController.decrementProductQuantity(session, 1L);
        //Assert
        assertNotNull(responseBody);
        assertEquals("500", responseBody);
    }

    @Test
    void decrementProductQuantity_nonActivateCustomer_expect401() {
        //Arrange
        when(userAuthorizer.isActivatedCustomer(session)).thenReturn(false);
        //Act
        String responseBody = customerController.decrementProductQuantity(session, 1L);
        //Assert
        assertNotNull(responseBody);
        assertEquals("401", responseBody);
    }

    @Test
    void registerPost_customerValidAndEmailSent_expectVerifyViewString() {
        //Arrange
        Customer customer = new Customer();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(sendEmailService.getRandom()).thenReturn("132465");
        when(customerService.create(customer)).thenReturn(true);
        when(sendEmailService.sendEmail(customer, EmailType.ACTIVATION, session)).thenReturn(true);
        //Act
        String viewName = customerController.register(customer, bindingResult, session);
        //Assert
        assertNotNull(viewName);
        assertEquals("redirect:/customer/verify.htm", viewName);
    }

    @Test
    void registerPost_customerValidAndEmailNotSent_expectRegistrationViewString() {
        //Arrange
        Customer customer = new Customer();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(sendEmailService.getRandom()).thenReturn("132465");
        when(customerService.create(customer)).thenReturn(true);
        when(sendEmailService.sendEmail(customer, EmailType.ACTIVATION, session)).thenReturn(false);
        //Act
        String viewName = customerController.register(customer, bindingResult, session);
        //Assert
        assertNotNull(viewName);
        assertEquals("registration", viewName);
    }

    @Test
    void registerPost_frontEndErrors_expectRegistrationViewString() {
        //Arrange
        when(bindingResult.hasErrors()).thenReturn(true);
        //Act
        String viewName = customerController.register(new Customer(), bindingResult, session);
        //Assert
        assertNotNull(viewName);
        assertEquals("registration", viewName);
    }
}
