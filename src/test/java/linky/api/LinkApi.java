package linky.api;

import linky.dto.*;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static linky.BasicIntegrationTest.TEST_PASSWORD;
import static linky.BasicIntegrationTest.TEST_USER_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Component
public class LinkApi extends BaseApi {

	@Autowired
	public LinkApi(RestTemplate restTemplate) {
		super(restTemplate);
	}

	public RestResponsePage<LinkBeanSimple> findMyLinks() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth(TEST_USER_EMAIL, TEST_PASSWORD, StandardCharsets.UTF_8);

		HttpEntity<CreateLinkBean> request = new HttpEntity<>(httpHeaders);

		//there are also page & size params
		Map<String, String> params = new HashMap<>();

		ResponseEntity<RestResponsePage<LinkBeanSimple>> responseEntity = restTemplate.exchange(
				localUrl + "/api/links",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<RestResponsePage<LinkBeanSimple>>() {
				},
				params
		);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		return responseEntity.getBody();
	}

	public LinkBean findLink(String id) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth(TEST_USER_EMAIL, TEST_PASSWORD, StandardCharsets.UTF_8);

		HttpEntity<String> request = new HttpEntity<>(httpHeaders);

		ResponseEntity<LinkBean> responseEntity = restTemplate.exchange(
				localUrl + "/api/link/" + id,
				HttpMethod.GET,
				request,
				LinkBean.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		LinkBean linkBean = responseEntity.getBody();
		assertNotNull(linkBean);
		return linkBean;
	}

	public LinkBean createLinkAndAssert(String name, String url) {
		LinkBean linkBean = createLink(name, url);
		assertNotNull(linkBean);
		assertNotNull(linkBean.id);
		assertEquals(url, linkBean.url);
		assertEquals(name, linkBean.name);
		assertEquals(0, linkBean.visits.size());
		//todo think about the visits

		return linkBean;
	}

	public LinkBean createLink(String name, String url) {
		return createLink(name, url, TEST_USER_EMAIL);
	}

	public LinkBean createLink(String name, String url, String userEmail) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth(userEmail, TEST_PASSWORD, StandardCharsets.UTF_8);

		HttpEntity<CreateLinkBean> request = new HttpEntity<>(new CreateLinkBean(name, url), httpHeaders);

		ResponseEntity<LinkBean> responseEntity = restTemplate.exchange(
				localUrl + "/api/link/create",
				HttpMethod.POST,
				request,
				LinkBean.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		return responseEntity.getBody();
	}

	public LinkBean updateLinkAndAssert(String linkId, String name, String url) {
		LinkBean linkBean = updateLink(linkId, name, url);
		assertNotNull(linkBean);
		assertEquals(linkId, linkBean.id);
		assertEquals(url, linkBean.url);
		assertEquals(name, linkBean.name);
		assertEquals(0, linkBean.visits.size());
		//todo think about the visits

		return linkBean;
	}

	public LinkBean updateLink(String linkId, String name, String url) {
		return updateLink(linkId, name, url, TEST_USER_EMAIL);
	}

	public LinkBean updateLink(String linkId, String name, String url, String userEmail) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth(userEmail, TEST_PASSWORD, StandardCharsets.UTF_8);

		HttpEntity<UpdateLinkBean> request = new HttpEntity<>(new UpdateLinkBean(name, url), httpHeaders);

		ResponseEntity<LinkBean> responseEntity = restTemplate.exchange(
				localUrl + "/api/link/update/" + linkId,
				HttpMethod.POST,
				request,
				LinkBean.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		return responseEntity.getBody();
	}
}
