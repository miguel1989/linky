package linky.api;

import linky.dto.CreateLinkBean;
import linky.dto.LinkBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static linky.BasicIntegrationTest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Component
public class LinkAdminApi extends BaseApi {

	@Autowired
	public LinkAdminApi(RestTemplate restTemplate) {
		super(restTemplate);
	}
	
	public LinkBean findLinkSuccessAndAssert(String id) {
		ResponseEntity<LinkBean> response = findLink(id);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));

		LinkBean linkBean = response.getBody();
		assertThat(linkBean, is(notNullValue()));
		assertThat(linkBean.id, is(id));
		return linkBean;
	}

	public ResponseEntity<LinkBean> findLink(String id) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.AUTHORIZATION,
				buildBasicAuth(TEST_ADMIN_EMAIL, TEST_PASSWORD));

		HttpEntity<CreateLinkBean> request = new HttpEntity<>(httpHeaders);

		return restTemplate.exchange(
				localUrl + "/admin/link/" + id,
				HttpMethod.GET,
				request,
				LinkBean.class);
	}
}
