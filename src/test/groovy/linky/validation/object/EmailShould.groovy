package linky.validation.object

import spock.lang.Specification

class EmailShould extends Specification {

	def 'validation'() {
		setup:
		Email email = new Email(text)

		expect:
		email.isValid() == expectedResult

		where:
		text                | expectedResult
		null                | false
		''                  | false
		'   '               | false
		'something'         | false
		'qqq@'              | false
		'qqqq@qqqq.qqqq123' | false
		'bruce@wayne.com'   | true
	}
}
