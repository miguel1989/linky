package linky.reaction

import linky.command.VisitLink
import linky.dao.LinkDao
import linky.domain.Link
import linky.dto.VisitLinkBean
import spock.lang.Specification

class VisitLinkReactionShould extends Specification {

	VisitLinkReaction visitLinkReaction
	LinkDao linkDao

	void setup() {
		linkDao = Mock(LinkDao)
		visitLinkReaction = new VisitLinkReaction(linkDao)
	}

	def 'not found link'() {
		setup:
		linkDao.findByName('gogle') >> Optional.empty()

		when:
		VisitLinkBean result = visitLinkReaction.react(new VisitLink('gogle', '127.0.0.1'))

		then:
		result.url == 'not_found'
	}

	def 'found link'() {
		setup:
		Link link = new Link('gogle', 'http://www.google.lv', 'user@linky.lv')
		linkDao.findByName('gogle') >> Optional.of(link)

		when:
		VisitLinkBean result = visitLinkReaction.react(new VisitLink('gogle', '127.0.0.1'))

		then:
		result.url == 'http://www.google.lv'
		link.visits().size() == 1
	}

}
