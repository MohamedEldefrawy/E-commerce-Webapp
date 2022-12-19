//package service;
//
//import com.vodafone.Ecommerce.exception.admin.CreateAdminException;
//import com.vodafone.Ecommerce.exception.admin.GetAdminException;
//import com.vodafone.Ecommerce.model.Admin;
//import com.vodafone.Ecommerce.model.Role;
//import com.vodafone.Ecommerce.model.UserStatus;
//import com.vodafone.Ecommerce.repository.admin.AdminRepository;
//import com.vodafone.Ecommerce.service.AdminService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class AdminServiceUnitTest {
//
//
//    private  AdminRepository adminRepositoryMock;
//    private  AdminService adminService;
//    private List<Admin> adminList;
//    private Admin admin;
//
//    @BeforeEach
//    public void setup() {
//        adminRepositoryMock = mock(AdminRepository.class); //create mock repo
//        adminService = new AdminService(adminRepositoryMock); //create admin service
//        adminList = new ArrayList<>(); //initialize empty admins array
//        admin = new Admin(); //create new admin
//        admin.setUserName("admin");
//        admin.setEmail("admin@gmail.com");
//        admin.setPassword("12345678");
//        admin.setFirstLogin(false);
//        admin.setRole(Role.Admin);
//        admin.setUserStatus(UserStatus.ADMIN);
//        admin.setId(2L);
//        adminList.add(admin);//insert admin into array
//    }
//
//    @Test
//    public void getAllAdminsTest_returnAllSavedAdmins() {
//        //Arrange
//        when(adminRepositoryMock.getAll()).thenReturn(Optional.of(adminList));
//        //Act
//        List<Admin> returnedAdminList = adminService.getAll();
//        //Assert
//        assertNotNull(returnedAdminList);
//        assertEquals(1, returnedAdminList.size());
//        verify(adminRepositoryMock, times(1)).getAll();
//    }
//
//    @Test
//    public void getAllAdminsTest_throwsGetAdminException() {
//        //Arrange
//        when(adminRepositoryMock.getAll()).thenReturn(Optional.empty());
//        //Act
//        assertThrows(GetAdminException.class, adminService::getAll);
//    }
//
//    @Test
//    public void getAdminByIdTest_sendId_returnSavedAdmin() {
//        //Arrange
//        when(adminRepositoryMock.getById(any(Long.class))).thenReturn(Optional.of(admin));
//        //Act
//        Admin returnedAdmin = adminService.getAdminById(2L);
//        //Asset
//        assertNotNull(returnedAdmin);
//        assertEquals("admin", returnedAdmin.getUserName());
//        verify(adminRepositoryMock, times(1)).getById(any());
//    }
//
//    @Test
//    public void getAdminByIdTest_sendNonExistentId_ThrowException() {
//        //Arrange
//        when(adminRepositoryMock.getById(any(Long.class))).thenReturn(Optional.empty());
//        //Act & Assert
//        assertThrows(GetAdminException.class, () -> adminService.getAdminById(3L));
//        verify(adminRepositoryMock, times(1)).getById(any());
//    }
//
//    @Test
//    public void deleteAdminByIdTest_sendExistingId_returnTrue() {
//        //Arrange
//        when(adminRepositoryMock.getById(any())).thenReturn(Optional.of(admin));
//        when(adminRepositoryMock.delete(any())).thenReturn(true);
//        //Act
//        boolean deleted = adminService.deleteAdmin(2L);
//        //Asset
//        assertTrue(deleted);
//        verify(adminRepositoryMock, times(1)).getById(any());
//        verify(adminRepositoryMock, times(1)).delete(any());
//    }
//
//    @Test
//    public void deleteAdminByIdTest_sendNonExistingId_returnFalse() {
//        //Arrange
//        when(adminRepositoryMock.getById(any())).thenReturn(Optional.empty());
//        //Act & Assert
//        assertThrows(GetAdminException.class, () -> adminService.deleteAdmin(3L));
//        verify(adminRepositoryMock, times(1)).getById(any());
//    }
//
//    @Test
//    public void addAdmin_sendAdmin_saveToDBReturnTrue() {
//        //Arrange
//        when(adminRepositoryMock.create(admin)).thenReturn(Optional.of(1L));
//        //Act
//        boolean created = adminService.createAdmin(admin);
//        //Asset
//        assertTrue(created);
//        verify(adminRepositoryMock, times(1)).create(any());
//    }
//
//    @Test
//    public void addAdmin_sendAdminWithDuplicateUsername_throwException() {
//        //Arrange
//        when(adminRepositoryMock.create(admin)).thenThrow(CreateAdminException.class);
//        //Act & Assert
//        assertThrows(CreateAdminException.class, () -> adminService.createAdmin(admin));
//        verify(adminRepositoryMock, times(1)).create(any());
//    }
//    @Test
//    public void addAdmin_sendAdminWithNullUsername_throwException() {
//        //Arrange
//        admin.setUserName(null);
//        //Act & Assert
//        assertThrows(CreateAdminException.class, ()->adminService.createAdmin(admin));
//        verify(adminRepositoryMock, times(0)).create(any());
//    }
//
//    @Test
//    public void addAdmin_sendAdminWithNullEmail_throwException() {
//        //Arrange
//        admin.setEmail(null);
//        //Act & Assert
//        assertThrows(CreateAdminException.class, ()->adminService.createAdmin(admin));
//        verify(adminRepositoryMock, times(0)).create(any());
//    }
//
//    @Test
//    public void addAdmin_sendAdminWithNullPassword_throwException() {
//        //Arrange
//        admin.setPassword(null);
//        //Act & Assert
//        assertThrows(CreateAdminException.class, ()->adminService.createAdmin(admin));
//        verify(adminRepositoryMock, times(0)).create(any());
//    }
//
//    @Test
//    public void addAdmin_sendAdminGetEmpty_throwException() {
//        //Arrange
//        when(adminRepositoryMock.create(admin)).thenReturn(Optional.empty());
//        //Act & Assert
//        assertThrows(CreateAdminException.class, () -> adminService.createAdmin(admin));
//        verify(adminRepositoryMock, times(1)).create(any());
//    }
//
//    @Test
//    public void updateAdmin_sendIdAndAdmin_saveToDBReturnTrue() {
//        //Arrange
//        when(adminRepositoryMock.getById(any())).thenReturn(Optional.of(admin));
//        when(adminRepositoryMock.update(2L, admin)).thenReturn(true);
//        //Act
//        boolean updated = adminService.updateAdmin(2L, admin);
//        //Asset
//        assertTrue(updated);
//        verify(adminRepositoryMock, times(1)).getById(any());
//        verify(adminRepositoryMock, times(1)).update(any(), any());
//    }
//
//    @Test
//    public void updateAdmin_sendNonExistentIdAndAdmin_throwException() {
//        //Arrange
//        when(adminRepositoryMock.getById(3L)).thenReturn(Optional.empty());
//        //Act & Assert
//        assertThrows(GetAdminException.class, () -> adminService.updateAdmin(3L, admin));
//        verify(adminRepositoryMock, times(1)).getById(any());
//    }
//
//    @Test
//    public void updateAdminPassword_sendIdAndAdmin_saveToDBReturnTrue() {
//        //Arrange
//        when(adminRepositoryMock.getById(any())).thenReturn(Optional.of(admin));
//        when(adminRepositoryMock.updatePassword(any(Long.class), any())).thenReturn(true);
//        //Act
//        boolean updated = adminService.updatePassword(2L, "2938012893801");
//        //Asset
//        assertTrue(updated);
//        verify(adminRepositoryMock, times(1)).getById(any());
//        verify(adminRepositoryMock, times(1)).updatePassword(any(), any());
//    }
//
//    @Test
//    public void updateAdminPassword_sendNonExistentIdAndAdmin_throwException() {
//        //Arrange
//        when(adminRepositoryMock.getById(any())).thenReturn(Optional.empty());
//        //Act & Assert
//        assertThrows(GetAdminException.class, () -> adminService.updatePassword(3L, "2817927839123"));
//        verify(adminRepositoryMock, times(1)).getById(any());
//    }
//
//    @Test
//    public void setFirstLogin_sendExistingId_returnTrue() {
//        //Arrange
//        when(adminRepositoryMock.getById(any())).thenReturn(Optional.of(admin));
//        when(adminRepositoryMock.setFirstLoginFlag(any())).thenReturn(true);
//        //Act
//        boolean updated = adminService.setFirstLoginFlag(2L);
//        //Assert
//        assertTrue(updated);
//        verify(adminRepositoryMock, times(1)).getById(any());
//        verify(adminRepositoryMock, times(1)).setFirstLoginFlag(any(Long.class));
//    }
//
//    @Test
//    public void setFirstLogin_sendNonExistentId_throwException() {
//        //Arrange
//        when(adminRepositoryMock.getById(any())).thenReturn(Optional.empty());
//        //Act & Assert
//        assertThrows(GetAdminException.class, () -> adminService.setFirstLoginFlag(3L));
//        verify(adminRepositoryMock, times(1)).getById(any(Long.class));
//    }
//
//    @Test
//    public void getByEmail_sendExistingEmail_returnAdmin() {
//        //Arrange
//        when(adminRepositoryMock.getByEmail(any(String.class))).thenReturn(Optional.of(admin));
//        //Act
//        Admin retrievedAdmin = adminService.getAdminByEmail("admin@gmail.com");
//        //Asset
//        assertNotNull(retrievedAdmin);
//        verify(adminRepositoryMock, times(1)).getByEmail(any());
//    }
//
//    @Test
//    public void getByEmail_sendNonExistentEmail_throwException() {
//        //Arrange
//        when(adminRepositoryMock.getByEmail(any(String.class))).thenReturn(Optional.empty());
//        //Act & Assert
//        assertThrows(GetAdminException.class, () -> adminService.getAdminByEmail("admi@gmail.com"));
//        verify(adminRepositoryMock, times(1)).getByEmail(any());
//    }
//
//    @Test
//    public void getByUsername_sendExistingUsername_returnAdmin() {
//        //Arrange
//        when(adminRepositoryMock.getByUsername(any(String.class))).thenReturn(Optional.of(admin));
//        //Act
//        Admin retrievedAdmin = adminService.getAdminByUsername("admin");
//        //Assert
//        assertNotNull(retrievedAdmin);
//        verify(adminRepositoryMock, times(1)).getByUsername(any());
//    }
//
//    @Test
//    public void getByUsername_sendNonExistentUsername_throwException() {
//        //Arrange
//        when(adminRepositoryMock.getByUsername(any(String.class))).thenReturn(Optional.empty());
//        //Act & Assert
//        assertThrows(GetAdminException.class, () -> adminService.getAdminByUsername("admi"));
//        verify(adminRepositoryMock, times(1)).getByUsername(any());
//    }
//}