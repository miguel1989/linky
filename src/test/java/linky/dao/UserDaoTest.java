package linky.dao;

import linky.domain.Role;
import linky.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @BeforeEach
    public void setup() {
        userDao.deleteAll();
    }

    @Test
    public void findAll() {
        User user = new User("batman@batman.com", "secret", "Bruce Wayne");
        userDao.save(user);

        Iterable<User> users = userDao.findAll();
//		assertThat(users).hasSize(1);
        User savedUser = users.iterator().next();
        assertNotNull(savedUser.id());
        assertNotNull(savedUser.createdAt());
        assertEquals("batman@batman.com", savedUser.email());
        assertEquals("secret", savedUser.password());
        assertEquals("Bruce Wayne", savedUser.name());
        assertEquals(1, savedUser.getAuthorities().size());
        Role role = (Role) savedUser.getAuthorities().iterator().next();
        assertEquals("ROLE_USER", role.getAuthority());
    }

    @Test
    public void findByEmail() {
        User user = new User("batman@batman.com", "secret", "Bruce Wayne");
        userDao.save(user);

        Optional<User> optionalUser = userDao.findByEmail("something");
        assertFalse(optionalUser.isPresent());

        optionalUser = userDao.findByEmail("batman@batman.com");
        assertTrue(optionalUser.isPresent());
    }
}
