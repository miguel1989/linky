package linky.integration;

import linky.BasicIntegrationTest;
import linky.dto.LinkBean;
import linky.dto.LinkBeanSimple;
import linky.dto.RestResponsePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
    public void visitLink() throws Exception {
        LinkBean linkBean = linkApi.createLinkAndAssert("gogle", "www.google.lv");

        mockMvc.perform(get(new URI(localUrl() + "/gogle")))
//				.andExpect(status().isOk())
                .andExpect(redirectedUrl("www.google.lv"));

        linkBean = linkAdminApi.findLinkSuccessAndAssert(linkBean.id);
        assertEquals("gogle", linkBean.name);
        assertEquals("www.google.lv", linkBean.url);
        assertEquals(1, linkBean.visits.size());
    }

    @Test
    public void adminFindLinks() {
        linkApi.createLinkAndAssert("1gogle1", "www.google.lv");
        linkApi.createLinkAndAssert("2gogle2", "www.google2.lv");
        linkApi.createLinkAndAssert("yaho", "www.yahoo.lv");
        linkApi.createLinkAndAssert("yaho5", "www.yahoo.lv");

        RestResponsePage<LinkBeanSimple> result = linkAdminApi.findLinks("gogle", null).getBody();
        assertEquals(2, result.getContent().size());

        result = linkAdminApi.findLinks("aho5", null).getBody();
        assertEquals(1, result.getContent().size());

        result = linkAdminApi.findLinks(null, "www.yahoo.lv").getBody();
        assertEquals(2, result.getContent().size());
    }
}
