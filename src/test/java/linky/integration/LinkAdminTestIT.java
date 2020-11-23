package linky.integration;

import linky.BasicIntegrationTest;
import linky.dto.LinkBean;
import linky.dto.LinkBeanSimple;
import linky.dto.RestResponsePage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkAdminTestIT extends BasicIntegrationTest {

	@Test
	public void findLinks() {
		linkApi.createLinkAndAssert("1gogle1", "www.google.lv");
		linkApi.createLinkAndAssert("2gogle2", "www.google2.lv");
		linkApi.createLinkAndAssert("yaho", "www.yahoo.lv");
		linkApi.createLinkAndAssert("yaho5", "www.yahoo.lv");

		RestResponsePage<LinkBeanSimple> result = linkAdminApi.findLinks("gogle").getBody();
		assertEquals(2, result.getContent().size());

		result = linkAdminApi.findLinks("aho5").getBody();
		assertEquals(1, result.getContent().size());

		result = linkAdminApi.findLinks( "www.yahoo.lv").getBody();
		assertEquals(2, result.getContent().size());
	}

	@Test
	public void deleteAnyLink() {
		LinkBean linkBean1 = linkApi.createLinkAndAssert("1gogle1", "www.google.lv");
		LinkBean linkBean2 = linkApi.createLinkAndAssert("2gogle2", "www.google2.lv");

		RestResponsePage<LinkBeanSimple> result = linkAdminApi.findLinks("gogle").getBody();
		assertEquals(2, result.getContent().size());

		linkAdminApi.deleteAnyLinkAndAssert(linkBean1.id);

		result = linkAdminApi.findLinks("gogle").getBody();
		assertEquals(1, result.getContent().size());
	}
}
