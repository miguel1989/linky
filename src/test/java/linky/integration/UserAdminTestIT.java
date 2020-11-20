package linky.integration;

import linky.BasicIntegrationTest;
import linky.dto.RestResponsePage;
import linky.dto.UserBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAdminTestIT extends BasicIntegrationTest {
	@Test
	public void findAllUsers() {
		userApi.registerUserAndAssert("superman@man.com");

		RestResponsePage<UserBean> pagedUsers = userAdminApi.findUsers("sup");
		assertEquals(1, pagedUsers.getContent().size());
	}
}
