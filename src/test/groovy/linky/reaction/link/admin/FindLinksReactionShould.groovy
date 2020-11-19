package linky.reaction.link.admin

import linky.command.link.FindLinks
import linky.dao.LinkDao
import linky.domain.Link
import linky.dto.PageLinksBeanSimple
import linky.reaction.link.admin.FindLinksReaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class FindLinksReactionShould extends Specification {

	FindLinksReaction findLinksReaction
	LinkDao linkDao

	void setup() {
		linkDao = Mock(LinkDao)
		findLinksReaction = new FindLinksReaction(linkDao)
	}

	def "no search params return empty response"() {
		expect:
		PageLinksBeanSimple result = findLinksReaction.react(new FindLinks(name, url))
		result.pageLinks.getTotalElements() == 0
		result.pageLinks.getContent().isEmpty()

		where:
		name | url
		""   | ""
		null | null
		""   | null
		null | ""
	}

	def "react on search"() {
		//todo think how to check that "%" was added to each search str
		setup:
		String nameSearch = "gogle"
		String urlSearch = "www.google"

		Page<Link> page = new PageImpl<>([new Link(name: 'gogle', url: 'www.google.lv')])
		linkDao.findAll(_ as org.springframework.data.jpa.domain.Specification, _ as Pageable) >> page

		when:
		PageLinksBeanSimple result = findLinksReaction.react(new FindLinks(nameSearch, urlSearch))

		then:
		result.pageLinks.totalElements == 1
		result.pageLinks.content.size() == 1
	}
}
