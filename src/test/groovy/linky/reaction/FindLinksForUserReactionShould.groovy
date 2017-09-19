package linky.reaction

import linky.command.link.FindLinksForUser
import linky.dao.LinkDao
import linky.domain.Link
import linky.dto.LinksBean
import spock.lang.Specification

class FindLinksForUserReactionShould extends Specification {

	FindLinksForUserReaction findLinksForUserReaction
	LinkDao linkDao

	void setup() {
		linkDao = Mock(LinkDao)
		findLinksForUserReaction = new FindLinksForUserReaction(linkDao)
	}

	def 'empty'() {
		setup:
		linkDao.findByCreatedBy('batman') >> []

		when:
		LinksBean result = findLinksForUserReaction.react(new FindLinksForUser('batman'))

		then:
		result.links.size() == 0
	}

	def 'two items'() {
		setup:
		linkDao.findByCreatedBy('batman') >> [
				new Link('link1', 'http://google.lv', 'batman'),
				new Link('link2', 'http://yandex.ru', 'batman')
		]

		when:
		LinksBean result = findLinksForUserReaction.react(new FindLinksForUser('batman'))

		then:
		result.links.size() == 2
	}
}
