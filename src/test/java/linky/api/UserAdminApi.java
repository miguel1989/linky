package linky.api;

import linky.dto.CreateLinkBean;
import linky.dto.RestResponsePage;
import linky.dto.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static linky.BasicIntegrationTest.TEST_ADMIN_EMAIL;
import static linky.BasicIntegrationTest.TEST_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
public class UserAdminApi extends BaseApi {

	@Autowired
	public UserAdminApi(RestTemplate restTemplate) {
		super(restTemplate);
	}

	public void deleteUserAndAssert(String email) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth(TEST_ADMIN_EMAIL, TEST_PASSWORD, StandardCharsets.UTF_8);
		HttpEntity<String> request = new HttpEntity<>(null, httpHeaders);

		ResponseEntity<String> response = restTemplate.exchange(
				localUrl + "/admin/user/" + email,
				HttpMethod.DELETE,
				request,
				String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("ok", response.getBody());
	}

	public RestResponsePage<UserBean> findUsers(String searchStr) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth(TEST_ADMIN_EMAIL, TEST_PASSWORD, StandardCharsets.UTF_8);

		HttpEntity<CreateLinkBean> request = new HttpEntity<>(httpHeaders);

		//there are also page & size params
		Map<String, String> params = new HashMap<>();
		params.put("search", searchStr);

		ResponseEntity<RestResponsePage<UserBean>> responseEntity = restTemplate.exchange(
				localUrl + "/admin/users?search={search}",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<RestResponsePage<UserBean>>() {
				},
				params
		);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		return responseEntity.getBody();
	}
}
