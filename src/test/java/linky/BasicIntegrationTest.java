package linky;

import linky.api.LinkAdminApi;
import linky.api.LinkApi;
import linky.api.UserApi;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.StandardCharsets;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = {Application.class, TestConfig.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles(Profiles.TEST)
public abstract class BasicIntegrationTest {

	@LocalServerPort
	int randomServerPort;

	@Autowired
	protected UserApi userApi;

	@Autowired
	protected LinkApi linkApi;

	@Autowired
	protected LinkAdminApi linkAdminApi;

	public static final String TEST_USER_EMAIL = "user@test.lv";
	public static final String TEST_ADMIN_EMAIL = "admin@test.lv";
	public static final String TEST_PASSWORD = "secret";

	@BeforeEach
	public void setup() {
		//todo find a better way to pass port to API
		userApi.useLocalUrl(localUrl());
		linkApi.useLocalUrl(localUrl());
		linkAdminApi.useLocalUrl(localUrl());

		userApi.deleteUserAndAssert(TEST_USER_EMAIL);
		userApi.registerUserAndAssert(TEST_USER_EMAIL);
	}

	@AfterEach
	public void cleanUp() {
		userApi.deleteUserAndAssert(TEST_USER_EMAIL);
	}

	public static String buildBasicAuth(String email, String pass) {
		String auth = email + ":" + pass;
		byte[] encodedAuth = Base64.encodeBase64(
				auth.getBytes(StandardCharsets.UTF_8));
		return "Basic " + new String(encodedAuth);
	}

	public String localUrl() {
		return "http://localhost:" + randomServerPort;
	}
}
