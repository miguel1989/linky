package linky.reaction

import linky.command.link.FindMyLink
import linky.dao.LinkDao
import linky.domain.Link
import linky.dto.LinkBean
import spock.lang.Specification

class FindMyLinkReactionShould extends Specification {

	FindMyLinkReaction findLinkReaction
	LinkDao linkDao

	void setup() {
		linkDao = Mock(LinkDao)
		findLinkReaction = new FindMyLinkReaction(linkDao)
	}

	def "correct react"() {
		setup:
		UUID uuid = UUID.randomUUID()
		linkDao.findById(uuid) >> Optional.of(new Link('myLink','www.batman.com', 'batman'))

		when:
		LinkBean linkBean = findLinkReaction.react(new FindMyLink(uuid.toString(), 'batman'))

		then:
		linkBean.name == 'myLink'
		linkBean.url == 'www.batman.com'
		linkBean.visits.size() == 0
	}

	def "correct react with optional.empty"() {
		setup:
		UUID uuid = UUID.randomUUID()
		linkDao.findById(uuid) >> Optional.empty()

		when:
		LinkBean linkBean = findLinkReaction.react(new FindMyLink(uuid.toString(), 'batman'))

		then:
		linkBean.name == null
		linkBean.url == null
		linkBean.visits == null
	}
}
