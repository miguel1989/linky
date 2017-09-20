package linky.integration;

import linky.BasicIntegrationTest;
import linky.dto.UserBean;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

public class RegisterUserIT extends BasicIntegrationTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void deleteAndRegister() {
		userApi.deleteUserAndAssert(TEST_USER_EMAIL);
		userApi.registerUserAndAssert(TEST_USER_EMAIL);
		//todo check that there is no users with this email registered
	}

	@Test
	public void duplicateEmail() {
		//todo investigate how to check the message...
		thrown.expect(HttpClientErrorException.class);
//		thrown.expectMessage(""); HttpClientErrorException return message as "400 null"
		userApi.registerUser(TEST_USER_EMAIL);
	}

	@Test
	public void notAnEmail() {
		//todo investigate how to check the message...
		thrown.expect(HttpClientErrorException.class);
		ResponseEntity<UserBean> response = userApi.registerUser("something");
	}
}
