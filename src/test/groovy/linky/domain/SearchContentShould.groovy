package linky.domain;

import spock.lang.Specification;

class SearchContentShould extends Specification {

	def 'link search content'() {
		setup:
		String searchStr
		Link link = new Link("gogle", "http://google.lv", "batman")
		link.newVisit("1.2.3.4")
		link.updateSearch()

		when:
		searchStr = new SearchContent(link).extract()

		then:
		searchStr.contains('gogle')
		searchStr.contains('http://google.lv')
		searchStr.contains('batman')
	}
}
