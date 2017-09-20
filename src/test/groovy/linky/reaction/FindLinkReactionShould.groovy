package linky.reaction

import linky.command.link.FindLink
import linky.dao.LinkDao
import linky.domain.Link
import linky.dto.LinkBean
import spock.lang.Specification

class FindLinkReactionShould extends Specification {

	FindLinkReaction findLinkReaction
	LinkDao linkDao

	void setup() {
		linkDao = Mock(LinkDao)
		findLinkReaction = new FindLinkReaction(linkDao)
	}

	def "correct react"() {
		setup:
		UUID uuid = UUID.randomUUID();
		linkDao.findOne(uuid) >> new Link('myLink','www.batman.com', 'batman')

		when:
		LinkBean linkBean = findLinkReaction.react(new FindLink(uuid.toString()))

		then:
		linkBean.name == 'myLink'
		linkBean.url == 'www.batman.com'
		linkBean.visits.size() == 0
	}
}
