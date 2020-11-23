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

import java.nio.charset.StandardCharsets;
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
		httpHeaders.setBasicAuth(TEST_ADMIN_EMAIL, TEST_PASSWORD, StandardCharsets.UTF_8);

		HttpEntity<String> request = new HttpEntity<>(httpHeaders);

		return restTemplate.exchange(
				localUrl + "/admin/link/" + id,
				HttpMethod.GET,
				request,
				LinkBean.class);
	}

	public ResponseEntity<RestResponsePage<LinkBeanSimple>> findLinks(String search) { //Page<LinkBeanSimple>
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth(TEST_ADMIN_EMAIL, TEST_PASSWORD, StandardCharsets.UTF_8);

		HttpEntity<CreateLinkBean> request = new HttpEntity<>(httpHeaders);

		//there are also page & size params
		Map<String, String> params = new HashMap<>();
		params.put("search", search);

		//	new TypeReference<PageImpl<LinkBeanSimple>>(){}
		return restTemplate.exchange(
				localUrl + "/admin/links?search={search}",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<RestResponsePage<LinkBeanSimple>>() {
				},
				params
		);
	}

	public void deleteAnyLinkAndAssert(String id) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth(TEST_ADMIN_EMAIL, TEST_PASSWORD, StandardCharsets.UTF_8);

		HttpEntity<String> request = new HttpEntity<>(httpHeaders);

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				localUrl + "/admin/link/" + id,
				HttpMethod.DELETE,
				request,
				String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
