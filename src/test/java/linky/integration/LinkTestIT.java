package linky.integration;

import linky.BasicIntegrationTest;
import linky.dto.UserBean;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LinkTestIT extends BasicIntegrationTest {

	@Test
	@Ignore
	public void dummy() {
		UserBean userBean = userApi.registerUserAndAssert("test@linky.lv");
		assertThat(userBean, is(notNullValue()));
	}
}