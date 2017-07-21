package linky.sideeffect

import linky.dao.VisitDao
import linky.domain.Visit
import linky.event.NewVisitOccurred
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class GeoEncodeShould extends Specification {

	GeoEncode geoEncode
	VisitDao visitDao
	RestTemplate restTemplate
	
	void setup() {
		visitDao = Mock(VisitDao)
		restTemplate = Mock(RestTemplate)
		geoEncode = new GeoEncode(visitDao, restTemplate)
	}
	
	def 'wrong visit id'() {
		setup:
		visitDao.findOne(_) >> null
		
		when:
		geoEncode.occur(new NewVisitOccurred(UUID.randomUUID(), '127.1.1.1'))
		
		then:
		0 * restTemplate.getForEntity(_, _)
	}

	def 'empty json response'() {
		setup:
		Visit visit = new Visit()
		visitDao.findOne(_) >> visit
		restTemplate.getForEntity(_, _) >> new ResponseEntity<>("{}", HttpStatus.OK)
		
		when:
		geoEncode.occur(new NewVisitOccurred(UUID.randomUUID(), '127.1.1.1'))
		
		then:
		visit.country() == 'No country =('
		visit.data() == '{}'
	}

	def 'normal json response'() {
		setup:
		Visit visit = new Visit()
		visitDao.findOne(_) >> visit
		String jsonResponse = '{"ip":"192.30.253.113","country_code":"US","country_name":"United States","region_code":"CA","region_name":"California","city":"San Francisco","zip_code":"94107","time_zone":"America/Los_Angeles","latitude":37.7697,"longitude":-122.3933,"metro_code":807}'
		restTemplate.getForEntity(_, _) >> new ResponseEntity<>(jsonResponse, HttpStatus.OK)

		when:
		geoEncode.occur(new NewVisitOccurred(UUID.randomUUID(), '192.30.253.113'))

		then:
		visit.country() == 'United States'
		visit.data() == jsonResponse
	}
	
	//todo test when HTPP STATUS is not OK
}
