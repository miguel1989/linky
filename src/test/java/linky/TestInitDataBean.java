package linky;

import linky.command.RegisterAdmin;
import linky.infra.PipedNow;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static linky.BasicIntegrationTest.TEST_ADMIN_EMAIL;
import static linky.BasicIntegrationTest.TEST_PASSWORD;

@Component
@Profile(Profiles.TEST)
public class TestInitDataBean implements InitializingBean {

	@Autowired
	private PipedNow pipedNow;

	@Override
	public void afterPropertiesSet() throws Exception {
		new RegisterAdmin(
				TEST_ADMIN_EMAIL,
				TEST_PASSWORD,
				"sponge bob").execute(pipedNow);
	}
}
