package linky.integration;

import linky.BasicIntegrationTest;
import linky.dto.LinkBean;
import linky.dto.LinkBeanSimple;
import linky.dto.RestResponsePage;
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
		LinkBean linkBean = linkApi.createLinkAndAssert("gogle", "www.google.lv");

		mockMvc.perform(get(new URI(localUrl() + "/gogle")))
//				.andExpect(status().isOk())
				.andExpect(redirectedUrl("www.google.lv"));

		linkBean = linkAdminApi.findLinkSuccessAndAssert(linkBean.id);
		assertThat(linkBean.name, is("gogle"));
		assertThat(linkBean.url, is("www.google.lv"));
		assertThat(linkBean.visits, hasSize(1));
	}

	@Test
	public void adminFindLinks() throws Exception {
		linkApi.createLinkAndAssert("1gogle1", "www.google.lv");
		linkApi.createLinkAndAssert("2gogle2", "www.google2.lv");
		linkApi.createLinkAndAssert("yaho", "www.yahoo.lv");
		linkApi.createLinkAndAssert("yaho5", "www.yahoo.lv");

		RestResponsePage<LinkBeanSimple> result = linkAdminApi.findLinks("gogle", null).getBody();
		assertThat(result.getContent(), hasSize(2));

		result = linkAdminApi.findLinks("aho5", null).getBody();
		assertThat(result.getContent(), hasSize(1));

		result = linkAdminApi.findLinks(null, "www.yahoo.lv").getBody();
		assertThat(result.getContent(), hasSize(2));
	}
}
