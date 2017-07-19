package linky.controller;

import linky.command.RegisterUser;
import linky.infra.PipedNow;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitService implements InitializingBean {
	
	@Autowired
	private PipedNow pipedNow;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		new RegisterUser(
				"user@linky.lv", 
				"secret", 
				"batman").execute(pipedNow);

		new RegisterUser(
				"admin@linky.lv",
				"secret",
				"batman").execute(pipedNow);
	}
}
