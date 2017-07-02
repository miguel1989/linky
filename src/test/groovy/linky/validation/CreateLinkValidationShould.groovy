package linky.validation

import linky.command.CreateLink
import linky.dao.LinkDao
import linky.exception.ValidationFailed
import spock.lang.Specification

class CreateLinkValidationShould extends Specification {

	CreateLinkValidation createLinkValidation
	LinkDao linkDao

	void setup() {
		linkDao = Mock(LinkDao)
		createLinkValidation = new CreateLinkValidation(
				linkDao: linkDao
		)
	}

	def "null command"() {
		when:
		createLinkValidation.validate(null)
		
		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Command can not be null'
	}

	def "null user id"() {
		when:
		createLinkValidation.validate(new CreateLink(null, 'gogle', 'www.gogle.lv'))
		
		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'UserId is empty'
	}

	def "empty user id"() {
		when:
		createLinkValidation.validate(new CreateLink(' ', 'gogle', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'UserId is empty'
	}

	def "user id not exist"() {
		when:
		createLinkValidation.validate(new CreateLink('batman', 'gogle', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'User does not exist'
	}
}
