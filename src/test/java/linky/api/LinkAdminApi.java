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
public class LinkAdminApi extends BaseApi {

    @Autowired
    public LinkAdminApi(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public LinkBean findLinkSuccessAndAssert(String id) {
        ResponseEntity<LinkBean> response = findLink(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        LinkBean linkBean = response.getBody();
        assertNotNull(linkBean);
        assertNotNull(linkBean.id);
        return linkBean;
    }

    public ResponseEntity<LinkBean> findLink(String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION,
                buildBasicAuth(TEST_ADMIN_EMAIL, TEST_PASSWORD));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(
                localUrl + "/admin/link/" + id,
                HttpMethod.GET,
                request,
                LinkBean.class);
    }

    public ResponseEntity<RestResponsePage<LinkBeanSimple>> findLinks(String name, String url) { //Page<LinkBeanSimple>
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION,
                buildBasicAuth(TEST_ADMIN_EMAIL, TEST_PASSWORD));

        HttpEntity<CreateLinkBean> request = new HttpEntity<>(httpHeaders);

        //there are also page & size params
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("url", url);

        //	new TypeReference<PageImpl<LinkBeanSimple>>(){}
        return restTemplate.exchange(
                localUrl + "/admin/links?name={name}&url={url}",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<RestResponsePage<LinkBeanSimple>>() {
                },
                params
        );
    }
}
