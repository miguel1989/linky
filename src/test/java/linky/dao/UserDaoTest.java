package linky.dao;

import linky.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
	
	@Autowired
	private UserDao userDao;

	@Before
	public void setup() {
		userDao.deleteAll();
	}
	
	@Test
	public void query() {
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
	}
}
