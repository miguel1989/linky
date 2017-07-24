package linky.integration;

import linky.BasicIntegrationTest;
import linky.dto.UserBean;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RegisterUserIT extends BasicIntegrationTest {

	@Test
	public void deleteAndRegister() {
		userApi.deleteUser(TEST_USER_EMAIL);
		
		UserBean userBean = userApi.registerUser(TEST_USER_EMAIL);
		assertThat(userBean.id, is(notNullValue()));
		assertThat(userBean.email, is(TEST_USER_EMAIL));
		assertThat(userBean.name, is(notNullValue()));

		userApi.deleteUser(TEST_USER_EMAIL);
		userApi.registerUser(TEST_USER_EMAIL);
		userApi.deleteUser(TEST_USER_EMAIL);
		//todo check that there is no users with tht email registered
	}
}
