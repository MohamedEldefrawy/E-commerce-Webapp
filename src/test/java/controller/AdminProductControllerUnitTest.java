package controller;

import cn.org.rapid_framework.web.session.wrapper.HttpSessionWrapper;
import com.vodafone.controller.AdminController;
import com.vodafone.exception.product.CreateProductException;
import com.vodafone.exception.product.GetProductException;
import com.vodafone.model.Product;
import com.vodafone.model.dto.CreateProduct;
import com.vodafone.model.dto.UpdateProductDto;
import com.vodafone.service.AdminService;
import com.vodafone.service.HashService;
import com.vodafone.service.ProductService;
import com.vodafone.service.SendEmailService;
import com.vodafone.util.AdminViews;
import com.vodafone.validators.AdminValidator;
import com.vodafone.validators.UserAuthorizer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminProductControllerUnitTest {
    private final AdminService adminService = mock(AdminService.class);
    private final ProductService productService = mock(ProductService.class);
    private final UserAuthorizer userAuthorizer = mock(UserAuthorizer.class);
    private final AdminValidator validator = mock(AdminValidator.class);
    private final HashService hashService = mock(HashService.class);
    private final SendEmailService emailService = mock(SendEmailService.class);
    private final BindingResult bindingResult = mock(BindingResult.class);
    private final FileOutputStream fileOutputStream = mock(FileOutputStream.class);
    private final AdminController adminController = new AdminController(adminService, productService, userAuthorizer, validator, hashService, emailService);

    private final HttpSession httpSession = mock(HttpSession.class);

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Test
    void homeTest_sendSessionAndModel_returnHomeViewString() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(productService.getAvailableProducts()).thenReturn(new ArrayList<>());
        Model model = new ConcurrentModel();
        HttpSession httpSession = new HttpSessionWrapper(null);
        String viewName = adminController.home(httpSession);
        assertNotNull(viewName);
        assertEquals("shared/home", viewName);
    }

    @Test
    void homeTest_sendSessionAndModel_returnRedirectLoginString() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);
        when(productService.getAvailableProducts()).thenReturn(new ArrayList<>());
        Model model = new ConcurrentModel();
        HttpSession httpSession = new HttpSessionWrapper(null);
        String viewName = adminController.home(httpSession);
        assertNotNull(viewName);
        assertEquals(AdminViews.LOGIN_REDIRECT, viewName);
    }

    @Test
    void showAllProductsTest_sendHttpSessionAndModel_ReturnProductsViewString() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(productService.getAvailableProducts()).thenReturn(new ArrayList<>());
        Model model = new ConcurrentModel();
        HttpSession httpSession = new HttpSessionWrapper(null);
        String viewName = adminController.showAllProducts(httpSession, model);
        assertNotNull(viewName);
        assertEquals(AdminViews.ADMIN_PRODUCTS, viewName);
    }

    @Test
    void showAllProductsTest_sendHttpSessionAndModel_ReturnRedirectLoginString() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);
        when(productService.getAvailableProducts()).thenReturn(new ArrayList<>());
        Model model = new ConcurrentModel();
        HttpSession httpSession = new HttpSessionWrapper(null);
        String viewName = adminController.showAllProducts(httpSession, model);
        assertNotNull(viewName);
        assertEquals(AdminViews.LOGIN_REDIRECT, viewName);
    }


    @Test
    void updateProductTest_sendHttpSessionAndModelAndProductId_ReturnUpdateProductString() {
        Product dummyProduct = createProduct();
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(productService.getById(any(Long.class))).thenReturn(dummyProduct);
        Model model = new ConcurrentModel();
        HttpSession httpSession = new HttpSessionWrapper(null);
        String viewName = adminController.updateProduct(httpSession, model, 1L);
        assertNotNull(viewName);
        assertEquals(AdminViews.ADMIN_UPDATE_PRODUCT, viewName);
    }

    @Test
    void updateProductTest_sendHttpSessionAndModelAndProductId_ReturnRedirectLoginString() {
        Product dummyProduct = createProduct();
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);
        when(productService.getById(any(Long.class))).thenReturn(dummyProduct);
        Model model = new ConcurrentModel();
        HttpSession httpSession = new HttpSessionWrapper(null);
        String viewName = adminController.updateProduct(httpSession, model, 1L);
        assertNotNull(viewName);
        assertEquals(AdminViews.LOGIN_REDIRECT, viewName);
    }

    @Test
    void updateProductTest_sendHttpSessionAndModelAndProductId_ThrowGetProductException_returnUpdateProductString() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(productService.getById(any(Long.class))).thenThrow(new GetProductException("No product found with selected id"));
        Model model = new ConcurrentModel();
        HttpSession httpSession = new HttpSessionWrapper(null);
        String viewName = adminController.updateProduct(httpSession, model, 1L);
        assertNotNull(viewName);
        assertEquals(AdminViews.ADMIN_UPDATE_PRODUCT, viewName);
    }

    @Test
    void submitUpdateTest_sendCreateProductDtoAndBindingResultAndHttpSessionAndId_returnAdminShowProductsRedirectString() {
        UpdateProductDto updateProductDto = new UpdateProductDto();
        updateProductDto.setCategory("Cat");
        updateProductDto.setName("Test");
        updateProductDto.setPrice(200D);
        updateProductDto.setInStock(10);
        updateProductDto.setId(1L);


        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productService.getById(any(Long.class))).thenReturn(createProduct());
        when(productService.update(any(Long.class), any(Product.class))).thenReturn(true);
        when(httpSession.getServletContext()).thenReturn(null);

        try {
            doNothing().when(fileOutputStream).write(new byte[0]);
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        HttpSession httpSession = new HttpSessionWrapper(null);
        String result = adminController.submitUpdate(updateProductDto, bindingResult, httpSession, 1L);
        assertNotNull(result);
        assertEquals(AdminViews.ADMIN_SHOW_PRODUCT_REDIRECT, result);
    }

    @Test
    void submitUpdateTest_sendCreateProductDtoAndBindingResultAndHttpSessionAndId_returnAdminUpdateProductsString() {
        UpdateProductDto updateProductDto = new UpdateProductDto();
        updateProductDto.setCategory("Cat");
        updateProductDto.setName("Test");
        updateProductDto.setPrice(200D);
        updateProductDto.setInStock(10);
        updateProductDto.setId(1L);


        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productService.getById(any(Long.class))).thenReturn(createProduct());
        when(productService.update(any(Long.class), any(Product.class))).thenReturn(false);
        when(httpSession.getServletContext()).thenReturn(null);

        try {
            doNothing().when(fileOutputStream).write(new byte[0]);
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        HttpSession httpSession = new HttpSessionWrapper(null);
        String result = adminController.submitUpdate(updateProductDto, bindingResult, httpSession, 1L);
        assertNotNull(result);
        assertEquals(AdminViews.ADMIN_UPDATE_PRODUCT, result);
    }

    @Test
    void submitUpdateTest_sendCreateProductDtoAndBindingResultAndHttpSessionAndId_ErrorBindingResult_returnAdminUpdateProductsString() {
        UpdateProductDto updateProductDto = new UpdateProductDto();
        updateProductDto.setCategory("Cat");
        updateProductDto.setName("Test");
        updateProductDto.setPrice(200D);
        updateProductDto.setInStock(10);
        updateProductDto.setId(1L);


        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        HttpSession httpSession = new HttpSessionWrapper(null);
        String result = adminController.submitUpdate(updateProductDto, bindingResult, httpSession, 1L);
        assertNotNull(result);
        assertEquals(AdminViews.ADMIN_UPDATE_PRODUCT, result);
    }

    @Test
    void submitUpdateTest_sendCreateProductDtoAndBindingResultAndHttpSessionAndId_ThrowsGetProductException_returnAdminUpdateProductsString() {
        UpdateProductDto updateProductDto = new UpdateProductDto();
        updateProductDto.setCategory("Cat");
        updateProductDto.setName("Test");
        updateProductDto.setPrice(200D);
        updateProductDto.setInStock(10);
        updateProductDto.setId(1L);


        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productService.getById(any(Long.class))).thenThrow(new GetProductException("No Product found"));
        when(productService.update(any(Long.class), any(Product.class))).thenReturn(false);
        when(httpSession.getServletContext()).thenReturn(null);

        HttpSession httpSession = new HttpSessionWrapper(null);
        String result = adminController.submitUpdate(updateProductDto, bindingResult, httpSession, 1L);
        assertNotNull(result);
        assertEquals(AdminViews.ADMIN_UPDATE_PRODUCT, result);
    }

    @Test
    void submitUpdateTest_sendCreateProductDtoAndBindingResultAndHttpSessionAndId_falseUserAuthorizer_returnAdminUpdateProductsString() {
        UpdateProductDto updateProductDto = new UpdateProductDto();
        updateProductDto.setCategory("Cat");
        updateProductDto.setName("Test");
        updateProductDto.setPrice(200D);
        updateProductDto.setInStock(10);
        updateProductDto.setId(1L);

        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);

        HttpSession httpSession = new HttpSessionWrapper(null);
        String result = adminController.submitUpdate(updateProductDto, bindingResult, httpSession, 1L);
        assertNotNull(result);
        assertEquals(AdminViews.LOGIN_REDIRECT, result);
    }


    @Test
    void createProductTest_sendHttpSessionAndModel_ReturnRedirectLoginString() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);
        Model model = new ConcurrentModel();
        HttpSession httpSession = new HttpSessionWrapper(null);
        String viewName = adminController.createProduct(httpSession, model);
        assertNotNull(viewName);
        assertEquals(AdminViews.LOGIN_REDIRECT, viewName);
    }


    @Test
    void createProductTest_sendHttpSessionAndModel_ReturnCreateProductString() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        Model model = new ConcurrentModel();
        HttpSession httpSession = new HttpSessionWrapper(null);
        String viewName = adminController.createProduct(httpSession, model);
        assertNotNull(viewName);
        assertEquals(AdminViews.ADMIN_CREATE_PRODUCT, viewName);
    }


    @Test
    void submitCreateTest_sendProductAndBindingResultAndHttpSessionAndId_returnAdminShowProductsRedirectString() {
        Product dummyProduct = createProduct();
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productService.create(dummyProduct)).thenReturn(true);
        when(httpSession.getServletContext()).thenReturn(null);

        try {
            doNothing().when(fileOutputStream).write(new byte[100]);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(e);
        }
        HttpSession httpSession = new HttpSessionWrapper(null);
        CreateProduct createProduct = new CreateProduct();
        String result = adminController.submitCreate(createProduct, bindingResult, null, httpSession);
        assertNotNull(result);
        assertEquals(AdminViews.ADMIN_SHOW_PRODUCT_REDIRECT, result);
    }

    @Test
    void submitCreateTest_sendProductAndBindingResultAndHttpSessionAndId_returnAdminCreateProductsString() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        HttpSession httpSession = new HttpSessionWrapper(null);
        CreateProduct createProduct = new CreateProduct();
        String result = adminController.submitCreate(createProduct, bindingResult, null, httpSession);

        assertNotNull(result);
        assertEquals(AdminViews.ADMIN_CREATE_PRODUCT, result);
    }

    @Test
    void submitCreateTest_sendProductAndBindingResultAndHttpSessionAndId_ThrowsCreateProductException_returnAdminCreateProductsString() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productService.create(any(Product.class))).thenThrow(new CreateProductException("Couldn't create product"));
        when(httpSession.getServletContext()).thenReturn(null);

        try {
            doNothing().when(fileOutputStream).write(new byte[0]);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(e);
        }
        HttpSession httpSession = new HttpSessionWrapper(null);
        CreateProduct createProduct = new CreateProduct();
        String result = adminController.submitCreate(createProduct, bindingResult, null, httpSession);
        assertNotNull(result);
        assertEquals(AdminViews.ADMIN_CREATE_PRODUCT, result);
    }

    @Test
    void submitCreateTest_sendProductAndBindingResultAndHttpSessionAndId_ThrowsCreateProductException_returnAdminLoginRedirectString() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);
        CreateProduct createProduct = new CreateProduct();
        String result = adminController.submitCreate(createProduct, bindingResult, null, httpSession);
        assertNotNull(result);
        assertEquals(AdminViews.LOGIN_REDIRECT, result);
    }


    private Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("dummyProduct");
        product.setPrice(200);
        product.setDescription("dummy product description");
        product.setRate(3f);
        product.setInStock(200);
        product.setImage("myImage.png");
        product.setCategory("Cats");
        return product;
    }

}
