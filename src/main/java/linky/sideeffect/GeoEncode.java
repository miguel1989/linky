package linky.sideeffect;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import linky.dao.VisitDao;
import linky.domain.Visit;
import linky.event.NewVisitOccurred;
import linky.infra.SideEffect;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

@Component
public class GeoEncode implements SideEffect<NewVisitOccurred> {

	private static final Logger logger = LoggerFactory.getLogger(GeoEncode.class);

	private final VisitDao visitDao;
	private final RestTemplate restTemplate;
	private final ObjectMapper mapper = new ObjectMapper();
	private static final String REQUEST_URL = "http://freegeoip.net/json/";
	private static final String JSON_COUNTRY_NAME = "country_name";
	private static final String NO_COUNTRY = "No country =(";

	//todo maybe for TEST use mock version to not to connect to real world url
	@Autowired
	public GeoEncode(VisitDao visitDao, RestTemplate restTemplate) {
		this.visitDao = visitDao;
		this.restTemplate = restTemplate;
	}

	@Override
	public void occur(NewVisitOccurred event) {
		logger.info("NewVisitOccurred");
		Optional<Visit> optVisit = visitDao.findById(event.visitId());

		//just silent return if the visit is not found. todo -> think here
		if (!optVisit.isPresent()) {
			logger.warn("Incorrect visit");
			return;
		}
		Visit visit = optVisit.get();

		ResponseEntity<String> response = restTemplate.getForEntity(REQUEST_URL + event.ip(), String.class);
		try {
			String strResponse = response.getBody();
			JsonNode root = mapper.readTree(strResponse);
			String country = NO_COUNTRY;
			if (root.has(JSON_COUNTRY_NAME)) {
				country = root.get(JSON_COUNTRY_NAME).asText();
			}
			if (StringUtils.isBlank(country)) {
				country = NO_COUNTRY;
			}
			visit.tag(country, strResponse);
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}

}
