package linky.api;

import linky.dto.RegisterUserBean;
import linky.dto.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static linky.BasicIntegrationTest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Component
public class UserApi extends BaseApi {

	@Autowired
	public UserApi(RestTemplate restTemplate) {
		super(restTemplate);
	}

	public UserBean registerUserAndAssert(String email) {
		ResponseEntity<UserBean> response = registerUser(email);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		UserBean userBean = response.getBody();
		assertThat(userBean, is(notNullValue()));
		assertThat(userBean.id, is(notNullValue()));
		assertThat(userBean.email, is(email));

		return userBean;
	}

	public ResponseEntity<UserBean> registerUser(String email) {
		RegisterUserBean registerUserBean = new RegisterUserBean(email, TEST_PASSWORD, "test user");

		HttpEntity<RegisterUserBean> request = new HttpEntity<>(registerUserBean);
		return restTemplate.exchange(
				localUrl + "/service/register",
				HttpMethod.POST,
				request,
				UserBean.class);
	}

	public void deleteUserAndAssert(String email) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.AUTHORIZATION,
				buildBasicAuth(TEST_ADMIN_EMAIL, TEST_PASSWORD));
		HttpEntity<String> request = new HttpEntity<>(null, httpHeaders);

		ResponseEntity<String> response = restTemplate.exchange(
				localUrl + "/admin/user/" + email,
				HttpMethod.DELETE,
				request,
				String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), is("ok"));
	}
}
