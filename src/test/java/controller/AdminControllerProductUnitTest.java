package controller;

import cn.org.rapid_framework.web.session.wrapper.HttpSessionWrapper;
import com.vodafone.controller.AdminController;
import com.vodafone.exception.product.CreateProductException;
import com.vodafone.exception.product.GetProductException;
import com.vodafone.model.Product;
import com.vodafone.model.dto.CreateProductDto;
import com.vodafone.model.dto.UpdateProductDto;
import com.vodafone.service.AdminService;
import com.vodafone.service.HashService;
import com.vodafone.service.ProductService;
import com.vodafone.service.SendEmailService;
import com.vodafone.util.AdminViews;
import com.vodafone.validators.AdminValidator;
import com.vodafone.validators.UserAuthorizer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class AdminControllerProductUnitTest {
    /*private final AdminService adminService = mock(AdminService.class);
    private final ProductService productService = mock(ProductService.class);
    private final UserAuthorizer userAuthorizer = mock(UserAuthorizer.class);
    private final AdminValidator validator = mock(AdminValidator.class);
    private final HashService hashService = mock(HashService.class);
    private final SendEmailService emailService = mock(SendEmailService.class);
    private final BindingResult bindingResult = mock(BindingResult.class);
    private final FileOutputStream fileOutputStream = mock(FileOutputStream.class);
    private final AdminController adminController = new AdminController(adminService, productService, userAuthorizer, validator, hashService, emailService);

    private final HttpSession httpSession = mock(HttpSession.class);

    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @SneakyThrows
    @Test
    void homeTest_sendAuthorizeAdmin_returnHomeView() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        mockMvc.perform(get("/admins/home.htm")).andExpect(view().name(AdminViews.ADMIN_HOME));
    }

    @SneakyThrows
    @Test
    void homeTest_sendUnAuthorizeAdmin_returnRedirectLogin() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);
        mockMvc.perform(get("/admins/home.htm")).andExpect(view().name(AdminViews.LOGIN_REDIRECT));
    }

    @Test
    @SneakyThrows
    void showAllProductsTest_sendAuthorizeAdmin_ReturnProductsView() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(productService.getAvailableProducts()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/admins/products/show.htm")).andExpect(view().name(AdminViews.ADMIN_PRODUCTS)).andExpect(model().attribute("products", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void showAllProductsTest_sendUnAuthorizeAdmin_ReturnRedirectLoginView() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);
        mockMvc.perform(get("/admins/products/show.htm")).andExpect(view().name(AdminViews.LOGIN_REDIRECT));
    }

    @Test
    @SneakyThrows
    void updateProductTest_sendAuthorizeAdminAndProductId_ReturnUpdateProductView() {
        Product dummyProduct = createProduct();
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(productService.getById(any(Long.class))).thenReturn(dummyProduct);
        mockMvc.perform(get("/admins/products/update.htm").param("id", "1"))
                .andExpect(view().name(AdminViews.ADMIN_UPDATE_PRODUCT))
                .andExpect(model().attribute("product", dummyProduct));
    }

    @Test
    @SneakyThrows
    void updateProductTest_sendUnAuthorizeAdminAndProductId_ReturnRedirectLogin() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);
        mockMvc.perform(get("/admins/products/update.htm").param("id", "1"))
                .andExpect(view().name(AdminViews.LOGIN_REDIRECT));
    }

    @Test
    @SneakyThrows
    void updateProductTest_AuthorizeAdminAndProductId_ThrowGetProductException_returnUpdateProduct() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(productService.getById(any(Long.class))).thenThrow(new GetProductException("No product found with selected id"));
        mockMvc.perform(get("/admins/products/update.htm").param("id", "1"))
                .andExpect(view().name(AdminViews.ADMIN_UPDATE_PRODUCT))
                .andExpect(model().attribute("product", new Product()));
    }

    @Test
    @SneakyThrows
    void submitUpdateTest_sendAuthorizeAdminAndBindingResultAndId_returnAdminShowProductsRedirectView() {
        UpdateProductDto updateProductDto = createUpdateProductDto();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(productService.getById(any(Long.class))).thenReturn(createProduct());
        when(productService.update(any(Long.class), any(Product.class))).thenReturn(true);


        mockMvc.perform(post("/admins/products/update.htm")
                        .flashAttr("product", updateProductDto)
                        .param("id", "1"))
                .andExpect(view().name(AdminViews.ADMIN_SHOW_PRODUCT_REDIRECT));
    }

    @Test
    void submitUpdateTest_sendUpdateProductDtoAndFalseBindingResultAndId_returnAdminUpdateProducts() {
        UpdateProductDto updateProductDto = createUpdateProductDto();
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        HttpSession httpSession = new HttpSessionWrapper(null);
        String result = adminController.submitUpdate(updateProductDto, bindingResult, httpSession, 1L);
        assertNotNull(result);
        assertEquals(AdminViews.ADMIN_UPDATE_PRODUCT, result);
    }

    @Test
    @SneakyThrows
    void submitUpdateTest_sendUnAuthorizeAdmin_returnLoginRedirect() {
        UpdateProductDto updateProductDto = createUpdateProductDto();
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);

        mockMvc.perform(post("/admins/products/update.htm")
                        .flashAttr("product", updateProductDto)
                        .param("id", "1"))
                .andExpect(view().name(AdminViews.LOGIN_REDIRECT));
    }

    @Test
    @SneakyThrows
    void createProductTest_sendUnAuthorizeAdmin_ReturnRedirectLogin() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);

        mockMvc.perform(get("/admins/products/create.htm"))
                .andExpect(view().name(AdminViews.LOGIN_REDIRECT));
    }


    @Test
    @SneakyThrows
    void createProductTest_sendAuthorizeAdmin_ReturnCreateProductView() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        mockMvc.perform(get("/admins/products/create.htm"))
                .andExpect(view().name(AdminViews.ADMIN_CREATE_PRODUCT));
    }


    @Test
    @SneakyThrows
    void submitCreateTest_sendProductAndBindingResult_returnAdminShowProductsRedirect() {
        CreateProductDto createProductDto = createCreateProductDto();
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productService.create(any(Product.class))).thenReturn(true);

        mockMvc.perform(post("/admins/products/create.htm")
                        .flashAttr("product", createProductDto))
                .andExpect(view().name(AdminViews.ADMIN_SHOW_PRODUCT_REDIRECT));
    }

    @Test
    void submitCreateTest_sendProductAndFalseBindingResult_returnAdminCreateProductsView() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        HttpSession httpSession = new HttpSessionWrapper(null);
        CreateProductDto createProductDto = new CreateProductDto();
        String result = adminController.submitCreate(createProductDto, bindingResult, httpSession);

        assertNotNull(result);
        assertEquals(AdminViews.ADMIN_CREATE_PRODUCT, result);
    }

    @Test
    @SneakyThrows
    void submitCreateTest_ThrowsCreateProductException_returnAdminCreateProductsView() {
        CreateProductDto createProductDto = createCreateProductDto();
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productService.create(any(Product.class))).thenThrow(new CreateProductException("Couldn't create product"));
        mockMvc.perform(post("/admins/products/create.htm")
                        .flashAttr("product", createProductDto))
                .andExpect(view().name(AdminViews.ADMIN_CREATE_PRODUCT));
    }

    @Test
    @SneakyThrows
    void submitCreateTest_unAuthorizeAdmin_returnAdminLoginRedirect() {
        CreateProductDto createProductDto = createCreateProductDto();
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);
        mockMvc.perform(post("/admins/products/create.htm")
                        .flashAttr("product", createProductDto))
                .andExpect(view().name(AdminViews.LOGIN_REDIRECT));
    }

    @Test
    @SneakyThrows
    void deleteProductTest_sendProductId_return200() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(productService.delete(any(Long.class))).thenReturn(true);

        MvcResult result = mockMvc.perform(delete("/admins/products/show.htm").param("id", "1"))
                .andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void deleteProductTest_UnAuthorizeAdmin_return401() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);
        MvcResult result = mockMvc.perform(delete("/admins/products/show.htm").param("id", "1"))
                .andReturn();
        assertEquals("401", result.getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void deleteProductTest_ThrowGetProductException_return500() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(productService.delete(any(Long.class))).thenThrow(new GetProductException("No product found"));
        MvcResult result = mockMvc.perform(delete("/admins/products/show.htm").param("id", "1"))
                .andReturn();
        assertEquals("500", result.getResponse().getContentAsString());
    }

    private Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("dummyProduct");
        product.setPrice(200D);
        product.setDescription("dummy product description");
        product.setRate(3f);
        product.setInStock(200);
        product.setImage("myImage.png");
        product.setCategory("Cats");
        return product;
    }

    private UpdateProductDto createUpdateProductDto() {
        UpdateProductDto updateProductDto = new UpdateProductDto();
        updateProductDto.setCategory("Cat");
        updateProductDto.setName("Test");
        updateProductDto.setPrice(200D);
        updateProductDto.setInStock(10);
        updateProductDto.setId(1L);
        updateProductDto.setDescription("description");
        return updateProductDto;
    }

    private CreateProductDto createCreateProductDto() {
        CreateProductDto createProductDto = new CreateProductDto();
        createProductDto.setName("test");
        createProductDto.setDescription("desc");
        createProductDto.setCategory("Cats");
        createProductDto.setPrice(100);
        createProductDto.setInStock(10);
        return createProductDto;
    }

     */

}
