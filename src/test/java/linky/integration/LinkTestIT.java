package linky.integration;

import linky.BasicIntegrationTest;
import linky.dto.LinkBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

public class LinkTestIT extends BasicIntegrationTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		super.setup();
		//setup the mock to use the web context
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void visitLink() throws Exception {
		userApi.registerUserAndAssert(TEST_USER_EMAIL);

		LinkBean linkBean = linkApi.createLinkAndAssert("gogle", "www.google.lv");

		mockMvc.perform(get(new URI(localUrl() + "/gogle")))
//				.andExpect(status().isOk())
				.andExpect(redirectedUrl("www.google.lv"));

		linkBean = linkAdminApi.findLinkSuccessAndAssert(linkBean.id);
		assertThat(linkBean.name, is("gogle"));
		assertThat(linkBean.url, is("www.google.lv"));
		assertThat(linkBean.visits, hasSize(1));
	}
}
