package linky.reaction

import linky.command.CreateLink
import linky.dao.LinkDao
import linky.domain.Link
import linky.infra.Command
import spock.lang.Specification

class CreateLinkReactionShould extends Specification {

	CreateLinkReaction createLinkReaction
	LinkDao linkDao

	void setup() {
		linkDao = Mock(LinkDao)
		createLinkReaction = new CreateLinkReaction(
				linkDao: linkDao
		)
	}

	def "correct react"() {
		when:
		Command.R result = createLinkReaction.react(
				new CreateLink('batman', 'gogle', 'www.google.lv'))

		then:
		result instanceof Command.R.Void
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
