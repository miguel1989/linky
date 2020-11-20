package linky.integration;

import linky.BasicIntegrationTest;
import linky.dto.LinkBean;
import linky.dto.LinkBeanSimple;
import linky.dto.RestResponsePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void myLinks() {
        linkApi.createLinkAndAssert("1gogle1", "www.google.lv");
        linkApi.createLinkAndAssert("2gogle2", "www.google2.lv");

        ResponseEntity<RestResponsePage<LinkBeanSimple>> responseEntity = linkApi.findMyLinks();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        RestResponsePage<LinkBeanSimple> result = responseEntity.getBody();
        assertEquals(2, result.getContent().size());
    }

}
