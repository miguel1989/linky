package linky.integration;

import linky.BasicIntegrationTest;
import linky.dto.UserBean;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RegisterUserIT extends BasicIntegrationTest {

	@Test
	public void normalRegistration() {
		//todo delete this user first
		UserBean userBean = userApi.registerUser("test@linky.lv");
		assertThat(userBean, is(notNullValue()));
		assertThat(userBean.id, is(notNullValue()));
		assertThat(userBean.email, is("test@linky.lv"));
	}
}
