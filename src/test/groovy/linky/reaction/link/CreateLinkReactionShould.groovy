package linky.reaction.link

import linky.command.link.CreateLink
import linky.dao.LinkDao
import linky.domain.Link
import linky.dto.LinkBean
import linky.reaction.link.CreateLinkReaction
import spock.lang.Specification

class CreateLinkReactionShould extends Specification {

	CreateLinkReaction createLinkReaction
	LinkDao linkDao

	void setup() {
		linkDao = Mock(LinkDao)
		createLinkReaction = new CreateLinkReaction(linkDao)
	}

	def "correct react"() {
		when:
		LinkBean linkBean = createLinkReaction.react(
				new CreateLink('batman', 'gogle', 'www.google.lv'))

		then:
		assert linkBean
		1 * linkDao.save(_ as Link) >> { args ->
			Link link = args[0] as Link
			assert link.id()
			assert link.createdAt()
			assert link.createdBy() == 'batman'
			assert link.name() == 'gogle'
			assert link.url() == 'www.google.lv'
			assert link.visits().size() == 0
		}
	}
}
