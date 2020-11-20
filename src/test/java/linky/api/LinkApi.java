package linky.api;

import linky.dto.CreateLinkBean;
import linky.dto.LinkBean;
import linky.dto.LinkBeanSimple;
import linky.dto.RestResponsePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static linky.BasicIntegrationTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Component
public class LinkApi extends BaseApi {

    @Autowired
    public LinkApi(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<RestResponsePage<LinkBeanSimple>> findMyLinks() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION,
                buildBasicAuth(TEST_USER_EMAIL, TEST_PASSWORD));

        HttpEntity<CreateLinkBean> request = new HttpEntity<>(httpHeaders);

        //there are also page & size params
        Map<String, String> params = new HashMap<>();

        return restTemplate.exchange(
                localUrl + "/api/links",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<RestResponsePage<LinkBeanSimple>>() {
                },
                params
        );
    }

    public LinkBean createLinkAndAssert(String name, String url) {
        ResponseEntity<LinkBean> response = createLink(name, url);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        LinkBean linkBean = response.getBody();
        assertNotNull(linkBean);
        assertNotNull(linkBean.id);
        assertEquals(url, linkBean.url);
        assertEquals(name, linkBean.name);
        assertEquals(0, linkBean.visits.size());
        //todo think about the visits

        return linkBean;
    }

    public ResponseEntity<LinkBean> createLink(String name, String url) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION,
                buildBasicAuth(TEST_USER_EMAIL, TEST_PASSWORD));

        HttpEntity<CreateLinkBean> request = new HttpEntity<>(new CreateLinkBean(name, url), httpHeaders);

        return restTemplate.exchange(
                localUrl + "/api/link/create",
                HttpMethod.POST,
                request,
                LinkBean.class);
    }
}
