package linky;

import linky.command.RegisterAdmin;
import linky.command.RegisterUser;
import linky.infra.PipedNow;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(Profiles.DEV)
public class TestInitDataBean implements InitializingBean {

	@Autowired
	private PipedNow pipedNow;

	@Override
	public void afterPropertiesSet() throws Exception {
		new RegisterUser(
				"user@linky.lv",
				"secret",
				"robin").execute(pipedNow);

		new RegisterAdmin(
				"admin@linky.lv",
				"secret",
				"batman").execute(pipedNow);
	}
}
