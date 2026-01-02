import org.example.User;
import org.example.UserDao;
import org.example.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddUser() {
        User user = new User();
        user.setName("Adam");
        user.setEmail("adam@example.com");

        when(userDao.create(any(User.class))).thenReturn(user);
        User result = userService.addUser(user);
        assertNotNull(result);
        verify(userDao).create(user);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setName("Adam");
        user.setEmail("adam@example.com");
        when(userDao.update(any(User.class))).thenReturn(user);
        User result = userService.updateUser(user);
        assertNotNull(result);
        verify(userDao).update(user);
    }

    @Test
    void getUserByIdTest() {
        User user = new User();
        user.setId(1L);
        user.setName("Adam");
        when(userDao.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals("Adam", result.get().getName());
    }

    @Test
    void deleteUserByIdTest() {
        doNothing().when(userDao).delete(1L);
        userService.deleteUser(1L);
        verify(userDao).delete(1L);

    }

    @Test
    void getAllUsers() {
        List<User> users = userDao.findAll();
        assertNotNull(users);
    }
}
