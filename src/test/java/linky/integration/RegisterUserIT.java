package linky.integration;

import linky.BasicIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

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
        int a = 0;
        //todo investigate how to check the message...
//		thrown.expect(HttpClientErrorException.class);
//		thrown.expectMessage(""); HttpClientErrorException return message as "400 null"

    }

    @Test
    public void notAnEmail() {
        Throwable exceptionThatWasThrown = Assertions.assertThrows(HttpClientErrorException.class, () -> {
            userApi.registerUser("something");
        });
        int a = 0;
    }
}
