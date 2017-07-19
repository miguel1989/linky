package linky.dao;

import linky.domain.Role;
import linky.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDaoTest {

	@Autowired
	private UserDao userDao;

	@Before
	public void setup() {
		userDao.deleteAll();
	}

	@Test
	public void findAll() {
		User user = new User("batman@batman.com", "secret", "Bruce Wayne");
		userDao.save(user);

		Iterable<User> users = userDao.findAll();
		assertThat(users).hasSize(1);
		User savedUser = users.iterator().next();
		assertThat(savedUser.id()).isNotNull();
		assertThat(savedUser.createdAt()).isNotNull();
		assertThat(savedUser.email()).isEqualTo("batman@batman.com");
		assertThat(savedUser.password()).isEqualTo("secret");
		assertThat(savedUser.name()).isEqualTo("Bruce Wayne");
		assertThat(savedUser.getAuthorities().size()).isEqualTo(1);
		Role role = (Role) savedUser.getAuthorities().iterator().next();
		assertThat(role.getAuthority()).isEqualTo("ROLE_USER");
	}
	
	@Test
	public void findByEmail() {
		User user = new User("batman@batman.com", "secret", "Bruce Wayne");
		userDao.save(user);

		Optional<User> optionalUser = userDao.findByEmail("something");
		assertThat(optionalUser.isPresent()).isFalse();

		optionalUser = userDao.findByEmail("batman@batman.com");
		assertThat(optionalUser.isPresent()).isTrue();
	}
}
