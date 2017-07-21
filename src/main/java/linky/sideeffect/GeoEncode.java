package linky.sideeffect;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import linky.dao.VisitDao;
import linky.domain.Visit;
import linky.event.NewVisitOccurred;
import linky.infra.SideEffect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class GeoEncode implements SideEffect<NewVisitOccurred> {

	private final VisitDao visitDao;
	private final RestTemplate restTemplate;
	private final ObjectMapper mapper = new ObjectMapper();
	private static final String REQUEST_URL = "http://freegeoip.net/json/";
	private static final String JSON_COUNTRY_NAME = "country_name";
	private static final String NO_COUNTRY = "No country =(";

	@Autowired
	public GeoEncode(VisitDao visitDao, RestTemplate restTemplate) {
		this.visitDao = visitDao;
		this.restTemplate = restTemplate;
	}

	@Override
	public void occur(NewVisitOccurred event) {
		Visit visit = visitDao.findOne(event.visitId());

		//just silent return if the visit is not found. todo -> think here
		if (visit == null) {
			return;
		}

		ResponseEntity<String> response = restTemplate.getForEntity(REQUEST_URL + event.ip(), String.class);
		try {
			String strResponse = response.getBody();
			JsonNode root = mapper.readTree(strResponse);
			String country = NO_COUNTRY;
			if (root.has(JSON_COUNTRY_NAME)) {
				country = root.get(JSON_COUNTRY_NAME).asText();
			}
			visit.tag(country, strResponse);
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}

}
