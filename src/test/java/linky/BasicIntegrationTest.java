package linky;

import linky.api.UserApi;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestConfig.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(Profiles.TEST)
public abstract class BasicIntegrationTest {

	@LocalServerPort
	int randomServerPort;

	@Autowired
	protected UserApi userApi;

	public static final String TEST_USER_EMAIL = "user@test.lv";
	public static final String TEST_ADMIN_EMAIL = "admin@test.lv";
	public static final String TEST_PASSWORD = "secret";

	@Before
	public void setup() {
		//todo find a better way to pass port to API
		userApi.useLocalUrl(localUrl());
	}

	public String localUrl() {
		return "http://localhost:" + randomServerPort;
	}
}
