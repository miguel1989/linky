package linky.validation.object

import linky.dao.LinkDao
import linky.domain.Link
import spock.lang.Specification

class UniqueLinkNameShould extends Specification {

	LinkDao linkDao
	UniqueLinkName uniqueLinkName

	void setup() {
		linkDao = Mock(LinkDao)
		uniqueLinkName = new UniqueLinkName(linkDao)
	}
	
	def 'not guaranteed'() {
		setup:
		linkDao.findByName('google') >> Optional.of(new Link())
		
		expect:
		!uniqueLinkName.guaranteed('google')
	}

	def 'guaranteed'() {
		setup:
		linkDao.findByName('google') >> Optional.empty()

		expect:
		uniqueLinkName.guaranteed('google')
	}
}
