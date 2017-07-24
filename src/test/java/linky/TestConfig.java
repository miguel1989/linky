package linky;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

	@Bean
	@Profile(Profiles.TEST)
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
