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

		RestResponsePage<LinkBeanSimple> result = linkAdminApi.findLinks("GogLe");
		assertEquals(2, result.getContent().size());
		assertEquals("2gogle2", result.getContent().get(0).name);
		assertEquals("1gogle1", result.getContent().get(1).name);

		result = linkAdminApi.findLinks("AHO5");
		assertEquals(1, result.getContent().size());

		result = linkAdminApi.findLinks( "www.yahoo.lv");
		assertEquals(2, result.getContent().size());
		assertEquals("yaho5", result.getContent().get(0).name);
		assertEquals("yaho", result.getContent().get(1).name);
	}

	@Test
	public void deleteAnyLink() {
		LinkBean linkBean1 = linkApi.createLinkAndAssert("1gogle1", "www.google.lv");
		LinkBean linkBean2 = linkApi.createLinkAndAssert("2gogle2", "www.google2.lv");

		RestResponsePage<LinkBeanSimple> result = linkAdminApi.findLinks("gogle");
		assertEquals(2, result.getContent().size());

		linkAdminApi.deleteAnyLinkAndAssert(linkBean1.id);

		result = linkAdminApi.findLinks("gogle");
		assertEquals(1, result.getContent().size());
	}
}
