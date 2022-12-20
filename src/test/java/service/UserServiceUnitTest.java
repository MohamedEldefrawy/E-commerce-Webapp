package service;

import com.vodafone.model.Admin;
import com.vodafone.model.Role;
import com.vodafone.model.User;
import com.vodafone.model.UserStatus;
import com.vodafone.repository.user.IUserRepository;
import com.vodafone.service.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceUnitTest {
    private final IUserRepository userRepository = mock(IUserRepository.class);
    private final UserService userService = new UserService(userRepository);

    @Test
    void getUserByEmailTest_sendEmail_returnUser() {
        User user = new Admin();
        user.setEmail("admin@admin.com");
        user.setUserName("admin");
        user.setId(1L);
        user.setRole(Role.Admin);
        user.setUserStatus(UserStatus.ACTIVATED);

        when(userRepository.findFirstByEmail(any(String.class))).thenReturn(user);
        User result = userService.getUserByEmail(user.getEmail());
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUserName(), result.getUserName());
        assertEquals(user.getEmail(), result.getEmail());
    }
}
