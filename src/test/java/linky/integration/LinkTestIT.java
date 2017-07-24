package linky.integration;

import linky.BasicIntegrationTest;
import org.junit.Test;

public class LinkTestIT extends BasicIntegrationTest {

	@Test
	public void simpleCreate() {
		userApi.registerUserAndAssert(TEST_USER_EMAIL);
		
		linkApi.createLinkAndAssert("gogle", "www.google.lv");
	}
}
