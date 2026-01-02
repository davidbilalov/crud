import org.example.HibernateUtil;
import org.example.User;
import org.example.UserDao;
import org.example.UserDaoImpl;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private UserDao userDao;

    @BeforeAll
    void setUp() {
        postgres.start();

        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgres.getUsername());
        System.setProperty("hibernate.connection.password", postgres.getPassword());

        HibernateUtil.getSessionFactory();
        userDao = new UserDaoImpl();
    }

    @AfterAll
    void tearDown() {
        HibernateUtil.shutdown();
        postgres.stop();
    }

    @Test
    void testCreateAndFindById() {
        User user = new User();
        user.setName("qwe");
        user.setEmail("qwe@example.com");
        user.setAge(30);

        User created = userDao.create(user);
        Optional<User> found = userDao.findById(created.getId());

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("qwe", found.get().getName());
    }
    @Test
    void testFindAll() {
        User user = new User();
        user.setName("asd");
        user.setEmail("asd@example.com");
        user.setAge(30);
        userDao.create(user);

        User user2 = new User();
        user2.setName("asd2");
        user2.setEmail("asd2@example.com");
        user2.setAge(30);
        userDao.create(user2);

        List<User> users = userDao.findAll();

        Assertions.assertNotNull(users);
        Assertions.assertTrue(users.size() >= 2);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setName("update");
        user.setEmail("update@example.com");
        user.setAge(30);

        User created = userDao.create(user);

        created.setName("update2");
        created.setEmail("update2@example.com");
        User updated = userDao.update(created);

        System.out.println(updated.getName());
        Assertions.assertNotEquals("update", updated.getName());
        Assertions.assertEquals("update2@example.com", updated.getEmail());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setName("rrr");
        user.setEmail("rrr@example.com");
        user.setAge(30);

        User created = userDao.create(user);

        userDao.delete(created.getId());

        Optional<User> found = userDao.findById(created.getId());
        Assertions.assertFalse(found.isPresent());
    }
}