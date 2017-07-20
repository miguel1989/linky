package linky.validation.object

import spock.lang.Specification

class LinkNameShould extends Specification {

	def 'is valid'() {
		expect:
		new LinkName(name).isValid() == expectedResult

		where:
		name          | expectedResult
		null          | false
		''            | false
		' '           | false
		'  '          | false
		'!'           | false
		'!@#$%^&*'    | false
		'a'           | true
		'1'           | true
		'abc-1'       | true
		'_abc-1'      | true
		'a_b_c-1'     | true
		'a-b-c-1'     | true
		'A-B-C-1'     | true
		'X-Y-Z-9'     | true
		'normal_text' | true
	}

	def 'too long'() {
		setup:
		String longText = ''
		for (int i = 0; i < 256; i++) {
			longText += 'a'
		}

		expect:
		!new LinkName(longText).isValid()
	}
}
