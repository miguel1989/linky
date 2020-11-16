package linky.api;

import linky.dto.RegisterUserBean;
import linky.dto.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static linky.BasicIntegrationTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Component
public class UserApi extends BaseApi {

    @Autowired
    public UserApi(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public UserBean registerUserAndAssert(String email) {
        ResponseEntity<UserBean> response = registerUser(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserBean userBean = response.getBody();
        assertNotNull(userBean);
        assertNotNull(userBean.id);
        assertEquals(email, userBean.email);

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

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ok", response.getBody());
    }
}
