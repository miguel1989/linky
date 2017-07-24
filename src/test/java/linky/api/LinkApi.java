package linky.api;

import linky.dto.CreateLinkBean;
import linky.dto.LinkBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static linky.BasicIntegrationTest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Component
public class LinkApi {

	private String localUrl;
	private RestTemplate restTemplate;

	@Autowired
	public LinkApi(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public LinkBean createLinkAndAssert(String name, String url) {
		ResponseEntity<LinkBean> response = createLink(name, url);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		LinkBean linkBean = response.getBody();
		assertThat(linkBean, is(notNullValue()));
		assertThat(linkBean.id, is(notNullValue()));
		assertThat(linkBean.url, is(url));
		assertThat(linkBean.name, is(name));
		assertThat(linkBean.visits, is(empty()));
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

	public void useLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}
}
