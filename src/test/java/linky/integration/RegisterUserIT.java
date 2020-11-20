package linky.integration;

import linky.BasicIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterUserIT extends BasicIntegrationTest {

	@Test
	public void deleteAndRegister() {
		userApi.deleteUserAndAssert(TEST_USER_EMAIL);
		userApi.registerUserAndAssert(TEST_USER_EMAIL);
		//todo check that there is no users with this email registered
	}

	@Test
	public void duplicateEmail() {
		Throwable exceptionThatWasThrown = Assertions.assertThrows(HttpClientErrorException.class, () -> {
			userApi.registerUser(TEST_USER_EMAIL);
		});
		assertEquals("400 : [E-mail already exists]", exceptionThatWasThrown.getMessage());
	}

	@Test
	public void notAnEmail() {
		Throwable exceptionThatWasThrown = Assertions.assertThrows(HttpClientErrorException.class, () -> {
			userApi.registerUser("something");
		});
		assertEquals("400 : [Not a valid e-mail]", exceptionThatWasThrown.getMessage());
	}
}
