package linky.reaction.link

import linky.command.link.VisitLink
import linky.dao.LinkDao
import linky.domain.Link
import linky.dto.VisitLinkBean
import linky.infra.EphemeralDomainEvents
import linky.infra.SideEffects
import linky.reaction.link.VisitLinkReaction
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.transaction.support.TransactionTemplate
import spock.lang.Specification

class VisitLinkReactionShould extends Specification {

	VisitLinkReaction visitLinkReaction
	LinkDao linkDao

	void setup() {
		linkDao = Mock(LinkDao)
		visitLinkReaction = new VisitLinkReaction(linkDao)

		SideEffects sideEffects = new SideEffects(Mock(ListableBeanFactory) {
			getBeansOfType(_) >> [:]
		})
		new EphemeralDomainEvents(sideEffects, Mock(TransactionTemplate))
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
