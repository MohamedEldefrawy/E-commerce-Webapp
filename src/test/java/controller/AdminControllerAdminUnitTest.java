package controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


class AdminControllerAdminUnitTest {

    /*private AdminService adminService;
    private UserAuthorizer userAuthorizer;
    private AdminValidator validator;
    private HashService hashService;
    private AdminController adminController;
    private HttpSession session;
    private Admin admin;
    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        adminService = mock(AdminService.class);
        ProductService productService = mock(ProductService.class);
        userAuthorizer = mock(UserAuthorizer.class);
        validator = mock(AdminValidator.class);
        hashService = mock(HashService.class);
        SendEmailService emailService = mock(SendEmailService.class);
        session = mock(HttpSession.class);
        adminController = new AdminController(adminService,productService,userAuthorizer,validator,
                hashService,emailService);
        admin = new Admin(); //create new admin
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);

        mockMvc = MockMvcBuilders
                .standaloneSetup(adminController)
                .build();
    }

    @Test
    void getAll_sendUnAuthorizedAdmin_getLoginPage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(false);
        //Act
        String page = adminController.getAll(session,model);
        //Assert
        assertEquals(AdminViews.LOGIN_REDIRECT, page);
    }
    @Test
    void getAll_throwGetAdminException_getAdminsPage() throws Exception{

        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        when(adminService.getAll()).thenThrow(GetAdminException.class);
        mockMvc.perform(get("/admins/admins.htm"))
                .andExpect(view().name(AdminViews.VIEW_ALL_ADMINS))
                .andExpect(model().attribute("admins",hasSize(0)));

    }
    @Test
    void getAll_getAdminsList_getAdminsPage() throws Exception{

        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        List<Admin> admins = new ArrayList<>();
        admins.add(admin);
        when(adminService.getAll()).thenReturn(admins);
        mockMvc.perform(get("/admins/admins.htm"))
                .andExpect(view().name(AdminViews.VIEW_ALL_ADMINS))
                .andExpect(model().attribute("admins",hasSize(1)))
                .andExpect(model().attribute("admins", hasItem(
                        allOf(
                                hasProperty("id", is(2L))
                        )
                )));
    }

    @Test
    void delete_sendUnAuthorizedAdmin_get401() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(false);
        //Act
        String page = adminController.delete(session,1L);
        //Assert
        assertEquals("401",page);
    }

    @Test
    void delete_sendSuperAdminId_get405() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        //Act
        String page = adminController.delete(session,2L);
        //Assert
        assertEquals("405",page);
    }

    @Test
    void delete_sendOwnId_get405() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(3L);
        //Act
        String page = adminController.delete(session,3L);
        //Assert
        assertEquals("405",page);
    }
    @Test
    void delete_sendIdToBeDeletedWithServerError_get500() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(2L);
        when(adminService.deleteAdmin(any())).thenReturn(false);
        //Act
        String page = adminController.delete(session,3L);
        //Assert
        assertEquals("500",page);
    }
    @Test
    void delete_sendIdToBeDeletedThrowException_get404() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(2L);
        when(adminService.deleteAdmin(any())).thenThrow(GetAdminException.class);
        //Act
        String page = adminController.delete(session,3L);
        //Assert
        assertEquals("500",page);
    }
    @Test
    void delete_sendIdToBeDeleted_get200() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        when(session.getAttribute("id")).thenReturn(2L);
        when(adminService.deleteAdmin(any())).thenReturn(true);
        //Act
        String page = adminController.delete(session,3L);
        //Assert
        assertEquals("200",page);
    }

    @Test
    void updateAdminPage_sendUnauthorizedAdmin_getLoginPage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(false);
        //Act
        String page = adminController.updateAdminPage(session,model,1L);
        //Assert
        assertEquals(AdminViews.LOGIN_REDIRECT,page);
    }

    @Test
    void updateAdminPage_sendAuthorizedAdminAndNonExistentId_getAdminsPage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        when(adminService.getAdminById(any())).thenThrow(GetAdminException.class);
        //Act
        String page = adminController.updateAdminPage(session,model,1L);
        //Assert
        assertEquals(AdminViews.VIEW_ALL_ADMINS,page);
    }

    @Test
    void updateAdminPage_sendAuthorizedAdminAndExistingId_getUpdatePage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        when(adminService.getAdminById(any())).thenReturn(admin);
        //Act
        String page = adminController.updateAdminPage(session,model,1L);
        //Assert
        assertEquals(AdminViews.UPDATE_ADMIN,page);
    }

    @Test
    void getCreateAdminPage_sendUnauthorizedAdmin_getLoginPage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(false);
        //Act
        String page = adminController.getCreateAdminPage(session,model);
        //Assert
        assertEquals(AdminViews.LOGIN_REDIRECT,page);
    }

    @Test
    void getCreateAdminPage_sendAuthorizedAdmin_getCreatePage() {
        //Arrange
        Model model = mock(Model.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        //Act
        String page = adminController.getCreateAdminPage(session,model);
        //Assert
        assertEquals(AdminViews.CREATE_ADMIN,page);
    }

    @Test
    void create_sendUnauthorizedAdmin_getLoginPage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(false);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        //Act
        String page = adminController.create(createAdmin,session,bindingResult);
        //Assert
        assertEquals(AdminViews.LOGIN_REDIRECT,page);
    }

    @Test
    void create_sendBindingErrors_getCreatePage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        //Act
        String page = adminController.create(createAdmin,session,bindingResult);
        //Assert
        assertEquals(AdminViews.CREATE_ADMIN,page);
    }

    @Test
    void create_sendValidationErrors_getCreatePage() throws Exception{
        //Arrange
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        //add errors to binding result
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            ((BindingResult)args[1]).rejectValue("email","duplicated");
            return null; // void method in a block-style lambda, so return null
        }).when(validator).validate(any(CreateAdmin.class),any(BindingResult.class));
        //Act
        mockMvc.perform(post("/admins/createAdmin.htm")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr("createAdmin", createAdmin))
                .andExpect(view().name(AdminViews.CREATE_ADMIN));
    }

    @Test
    void create_sendCreateException_getCreatePage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(adminService.createAdmin(any())).thenThrow(CreateAdminException.class);
        //Act
        String page = adminController.create(createAdmin,session,bindingResult);
        //Assert
        assertEquals(AdminViews.CREATE_ADMIN,page);
    }

    @Test
    void create_sendCreateNoException_getAdminsPage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(adminService.createAdmin(any())).thenReturn(true);
        //Act
        String page = adminController.create(createAdmin,session,bindingResult);
        //Assert
        assertEquals(AdminViews.ALL_ADMINS_REDIRECT,page);
    }

    @Test
    void update_sendUnauthorizedAdmin_getLoginPage() {
        //Arrange
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(false);
        //Act
        String page = adminController.updateAdmin(createAdmin,session,bindingResult,3L);
        //Assert
        assertEquals(AdminViews.LOGIN_REDIRECT,page);
    }

    @Test
    void update_sendBindingErrors_getUpdatePage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        //Act
        String page = adminController.updateAdmin(createAdmin,session,bindingResult,3L);
        //Assert
        assertEquals(AdminViews.UPDATE_ADMIN,page);
    }

   @Test
    void update_sendValidationErrors_getUpdatePage() throws Exception{
        //Arrange
        when(userAuthorizer.authorizeAdmin(any(HttpSession.class))).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        //add errors to binding result
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            ((BindingResult)args[1]).rejectValue("email","duplicated");
            return null; // void method in a block-style lambda, so return null
        }).when(validator).validate(any(CreateAdmin.class),any(BindingResult.class));
        //Act
       mockMvc.perform(post("/admins/updateAdmin.htm")
                       .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                       .sessionAttr("createAdmin", createAdmin)
                       .param("id", "3"))
               .andExpect(view().name(AdminViews.UPDATE_ADMIN));
    }

    @Test
    void update_sendInvalidId_getUpdatePage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(adminService.updateAdmin(any(),any())).thenThrow(GetAdminException.class);
        //Act
        String page = adminController.updateAdmin(createAdmin,session,bindingResult,3L);
        //Assert
        assertEquals(AdminViews.UPDATE_ADMIN,page);
    }

    @Test
    void update_sendValidId_getAdminsPage() {
        //Arrange
        when(userAuthorizer.authorizeAdmin(session)).thenReturn(true);
        CreateAdmin createAdmin = new CreateAdmin(admin.getId().intValue(),admin.getUserName(),admin.getEmail());
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(adminService.updateAdmin(any(),any())).thenReturn(true);
        //Act
        String page = adminController.updateAdmin(createAdmin,session,bindingResult,3L);
        //Assert
        assertEquals(AdminViews.ALL_ADMINS_REDIRECT,page);
    }

    @Test
    void setAdminPassword() {
        //Act
        String page = adminController.setAdminPassword();
        //Assert
        assertEquals(AdminViews.ADMIN_RESET_PASSWORD,page);
    }

    @Test
    void testSetAdminPassword_sendInvalidEmail_getResetPage() {
        //Arrange
        when(session.getAttribute(anyString())).thenReturn(String.class);
        when(adminService.getAdminByEmail(anyString())).thenThrow(GetAdminException.class);
        //Act
        String page = adminController.setAdminPassword("29019280192",session);
        //Assert
        assertEquals(AdminViews.ADMIN_RESET_PASSWORD,page);
    }
    @Test
    void testSetAdminPassword_sendValidEmailAndInvalidId_getResetPage() {
        //Arrange
        when(session.getAttribute(any())).thenReturn(String.class);
        when(adminService.getAdminByEmail(anyString())).thenReturn(admin);
        when(adminService.updatePassword(any(),any())).thenThrow(GetAdminException.class);
        when(hashService.encryptPassword(any(),any())).thenReturn("12jo2jj1oij2");
        //Act
        String page = adminController.setAdminPassword("29019280192",session);
        //Assert
        assertEquals(AdminViews.ADMIN_RESET_PASSWORD,page);
    }

    @Test
    void testSetAdminPassword_sendValidEmailAndValidId_getHomePage() {
        //Arrange
        when(session.getAttribute(any())).thenReturn(String.class);
        when(adminService.getAdminByEmail(anyString())).thenReturn(admin);
        when(adminService.updatePassword(any(),any())).thenReturn(true);
        when(hashService.encryptPassword(any(),any())).thenReturn("12jo2jj1oij2");
        //Act
        String page = adminController.setAdminPassword("29019280192",session);
        //Assert
        assertEquals("redirect:/admins/home.htm",page);
    }*/
}