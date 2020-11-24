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
		userApi.registerUserAndAssert("spiderman@man.com");

		RestResponsePage<UserBean> pagedUsers = userAdminApi.findUsers("sup");
		assertEquals(1, pagedUsers.getContent().size());

		pagedUsers = userAdminApi.findUsers("MAN");
		assertEquals(2, pagedUsers.getContent().size());
	}
}
