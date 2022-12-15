package service;

import cn.org.rapid_framework.web.session.wrapper.HttpSessionWrapper;
import com.vodafone.controller.AdminController;
import com.vodafone.service.AdminService;
import com.vodafone.service.HashService;
import com.vodafone.service.ProductService;
import com.vodafone.service.SendEmailService;
import com.vodafone.validators.AdminValidator;
import com.vodafone.validators.UserAuthorizer;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdminProductControllerUnitTest {
    private final AdminService adminService = mock(AdminService.class);
    private final ProductService productService = mock(ProductService.class);
    private final UserAuthorizer userAuthorizer = mock(UserAuthorizer.class);
    private final AdminValidator validator = mock(AdminValidator.class);
    private final HashService hashService = mock(HashService.class);
    private final SendEmailService emailService = mock(SendEmailService.class);
    private final AdminController adminController = new AdminController(adminService, productService, userAuthorizer, validator, hashService, emailService);

    @Test
    void homeTest_sendSessionAndModel_returnHomeViewString() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(productService.getAvailableProducts()).thenReturn(new ArrayList<>());
        Model model = new ConcurrentModel();
        HttpSession httpSession = new HttpSessionWrapper(null);
        String viewName = adminController.home(httpSession, model);
        assertNotNull(viewName);
        assertEquals("shared/home", viewName);
    }

    @Test
    void homeTest_sendSessionAndModel_returnRedirectLoginString() {
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(false);
        when(productService.getAvailableProducts()).thenReturn(new ArrayList<>());
        Model model = new ConcurrentModel();
        HttpSession httpSession = new HttpSessionWrapper(null);
        String viewName = adminController.home(httpSession, model);
        assertNotNull(viewName);
        assertEquals("redirect:/login.htm", viewName);
    }

}
