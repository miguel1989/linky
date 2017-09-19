package linky.api;

import org.springframework.web.client.RestTemplate;

public abstract class BaseApi {

	protected String localUrl;
	protected RestTemplate restTemplate;

	public BaseApi() {

	}

	public BaseApi(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void useLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}
}
