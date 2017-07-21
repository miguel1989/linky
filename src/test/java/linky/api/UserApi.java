package linky.api;

import linky.TestConfig;
import linky.dto.RegisterUserBean;
import linky.dto.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

@Component
public class UserApi {
	
	private String localUrl;
	
	@Autowired
	private TestConfig testConfig;
	
	public UserBean registerUser(String email) {
		RegisterUserBean registerUserBean = new RegisterUserBean();
		registerUserBean.email = email;
		registerUserBean.password = "secret";
		registerUserBean.name = "test user";
		
		HttpEntity<RegisterUserBean> request = new HttpEntity<>(registerUserBean);
		return testConfig.restTemplate().postForObject(localUrl + "/register", request, UserBean.class);
	}
	
	public void useLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}
}
