package linky.integration;

import linky.BasicIntegrationTest;
import linky.dto.LinkBean;
import linky.dto.LinkBeanSimple;
import linky.dto.RestResponsePage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

public class LinkTestIT extends BasicIntegrationTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		super.setup();
		//setup the mock to use the web context
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void createWithLongName() {
		Throwable exceptionThatWasThrown = Assertions.assertThrows(HttpClientErrorException.class, () -> {
			linkApi.createLinkAndAssert(RandomStringUtils.randomAlphanumeric(300), "www.google.lv");
		});
		assertEquals("400 : [Incorrect link name]", exceptionThatWasThrown.getMessage());
	}

	@Test
	public void createWithLongUrl() {
		Throwable exceptionThatWasThrown = Assertions.assertThrows(HttpClientErrorException.class, () -> {
			linkApi.createLinkAndAssert("gogle", RandomStringUtils.randomAlphanumeric(1200));
		});
		assertEquals("400 : [Incorrect link url]", exceptionThatWasThrown.getMessage());
	}

	@Test
	public void visitLink() throws Exception {
		LinkBean linkBean = linkApi.createLinkAndAssert("gogle", "www.google.lv");

		mockMvc.perform(get(new URI(localUrl() + "/gogle")))
//				.andExpect(status().isOk())
				.andExpect(redirectedUrl("www.google.lv"));

		linkBean = linkApi.findLink(linkBean.id);
		assertEquals("gogle", linkBean.name);
		assertEquals("www.google.lv", linkBean.url);
		assertEquals(1, linkBean.visits.size());

		RestResponsePage<LinkBeanSimple> result = linkApi.findMyLinks();
		assertEquals(1, result.getContent().size());
		assertEquals("gogle", result.getContent().get(0).name);
		assertEquals(1, result.getContent().get(0).visitCount);
	}

	@Test
	public void noAccessToOtherUsersLinks() {
		LinkBean linkBean1 = linkApi.createLink("gogle1", "www.google.lv");
		LinkBean linkBean2 = linkApi.createLink("gogle2", "www.google.lv", TEST_USER_EMAIL2);

		LinkBean linkBean = linkApi.findLink(linkBean1.id);
		assertEquals("gogle1", linkBean.name);

		Throwable exceptionThatWasThrown = Assertions.assertThrows(HttpClientErrorException.class, () -> {
			linkApi.findLink(linkBean2.id);
		});
		assertEquals("400 : [Link not found]", exceptionThatWasThrown.getMessage());
	}

	@Test
	public void myLinks() {
		linkApi.createLinkAndAssert("1gogle1", "www.google.lv");
		linkApi.createLinkAndAssert("2gogle2", "www.google2.lv");

		RestResponsePage<LinkBeanSimple> result = linkApi.findMyLinks();
		assertEquals(2, result.getContent().size());
		assertEquals("2gogle2", result.getContent().get(0).name);
		assertEquals("1gogle1", result.getContent().get(1).name);
	}

}
