package service;

import com.vodafone.model.Admin;
import com.vodafone.model.Role;
import com.vodafone.model.UserStatus;
import com.vodafone.repository.admin.AdminRepository;
import com.vodafone.service.AdminService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminServiceUnitTest {

    private final AdminRepository adminRepositoryMock = mock(AdminRepository.class);
    private final AdminService adminService = new AdminService(adminRepositoryMock);


    @Test
    public void getAllAdminsTest_returnAllSavedAdmins() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.getAll()).thenReturn(Optional.of(list));
        //Act
        List<Admin> returnedAdminList = adminService.getAll();
        //Assert
        assertNotNull(returnedAdminList);
        assertEquals(1, returnedAdminList.size());
        //verify(adminRepositoryMock, times(1)).getAll();
    }

    @Test
    public void getAllAdminsTest_returnAdminsById() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.getAll()).thenReturn(Optional.of(list));
        //Act
        List<Admin> returnedAdminList = adminService.getAll();
        //Assert
        assertNotNull(returnedAdminList);
        assertEquals(1, returnedAdminList.size());
        //verify(adminRepositoryMock, times(1)).getAll();
    }

    @Test
    public void getAdminByIdTest_sendId_returnSavedAdmin() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.getById(any(Long.class))).thenReturn(Optional.of(admin));
        //Act
        Admin returnedAdmin = adminService.get(2L);
        //Asset
        assertNotNull(returnedAdmin);
        assertEquals("admin", returnedAdmin.getUserName());
        //verify(regionRepositoryMock, times(1)).findById(any());
    }

    @Test
    public void getAdminByIdTest_sendId_returnNull() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.getById(any(Long.class))).thenReturn(null);
        //Act
        Admin returnedAdmin = adminService.get(3L);
        //Asset
        assertNull(returnedAdmin);
        //verify(regionRepositoryMock, times(1)).findById(any());
    }

    @Test
    public void deleteAdminByIdTest_sendExistingId_returnTrue() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.delete(2L)).thenReturn(true);
        //Act
        boolean deleted = adminService.delete(2L);
        //Asset
        assertTrue(deleted);
        //verify(regionRepositoryMock, times(1)).findById(any());
    }

    @Test
    public void deleteAdminByIdTest_sendNonExistingId_returnFalse() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.delete(3L)).thenReturn(false);
        //Act
        boolean deleted = adminService.delete(3L);
        //Asset
        assertFalse(deleted);
        //verify(regionRepositoryMock, times(1)).findById(any());
    }

    @Test
    public void addAdmin_sendAdmin_saveToDBReturnTrue() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.create(admin)).thenReturn(Optional.of(1L));
        //Act
        boolean created = adminService.create(admin);
        //Asset
        assertTrue(created);
    }

    @Test
    public void addAdmin_sendAdminWithDuplicateUsername_returnFalse() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.create(admin)).thenReturn(Optional.of(1L));
        //Act
        boolean created = adminService.create(admin);
        //Asset
        assertFalse(created);
    }

    @Test
    public void updateAdmin_sendIdAndAdmin_saveToDBReturnTrue() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.update(2L, admin)).thenReturn(true);
        //Act
        boolean updated = adminService.update(2L, admin);
        //Asset
        assertTrue(updated);
    }

    @Test
    public void updateAdmin_sendNonExistentIdAndAdmin_returnFalse() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.update(3L, admin)).thenReturn(false);
        //Act
        boolean updated = adminService.update(3L, admin);
        //Asset
        assertFalse(updated);
    }

    @Test
    public void updateAdminPassword_sendIdAndAdmin_saveToDBReturnTrue() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.updatePassword(any(Long.class), any())).thenReturn(true);
        //Act
        boolean updated = adminService.updatePassword(2L, "2938012893801");
        //Asset
        assertTrue(updated);
    }

    @Test
    public void updateAdminPassword_sendNonExistentIdAndAdmin_returnFalse() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.updatePassword(any(Long.class), any())).thenReturn(false);
        //Act
        boolean updated = adminService.updatePassword(3L, "2817927839123");
        //Asset
        assertFalse(updated);
    }

    @Test
    public void setFirstLogin_sendExistingId_returnVoid() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        doNothing().when(adminRepositoryMock).setFirstLoginFlag(any(Long.class));
        //Act
        adminService.setFirstLoginFlag(2L);
        //Assert
        verify(adminRepositoryMock, times(1)).setFirstLoginFlag(any(Long.class));
    }

    @Test
    public void getByEmail_sendExistingEmail_returnAdmin() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.getByEmail(any(String.class))).thenReturn(admin);
        //Act
        Admin retrievedAdmin = adminService.getByEmail("admin@gmail.com");
        //Asset
        assertNotNull(retrievedAdmin);
        verify(adminRepositoryMock, times(1)).getByEmail(any());
    }

    @Test
    public void getByEmail_sendNonExistentEmail_returnNull() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.getByEmail(any(String.class))).thenReturn(null);
        //Act
        Admin retrievedAdmin = adminService.getByEmail("admi@gmail.com");
        //Asset
        assertNull(retrievedAdmin);
        verify(adminRepositoryMock, times(1)).getByEmail(any());
    }

    @Test
    public void getByEmail_sendExistingUsername_returnAdmin() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.getByUsername(any(String.class))).thenReturn(admin);
        //Act
        Admin retrievedAdmin = adminService.getByUsername("admin");
        //Asset
        assertNotNull(retrievedAdmin);
        verify(adminRepositoryMock, times(1)).getByUsername(any());
    }

    @Test
    public void getByEmail_sendNonExistentUsername_returnNull() {
        //Arrange
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345678");
        admin.setFirstLogin(false);
        admin.setRole(Role.Admin);
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setId(2L);
        list.add(admin);
        when(adminRepositoryMock.getByUsername(any(String.class))).thenReturn(null);
        //Act
        Admin retrievedAdmin = adminService.getByUsername("admi");
        //Asset
        assertNull(retrievedAdmin);
        verify(adminRepositoryMock, times(1)).getByUsername(any());
    }
}