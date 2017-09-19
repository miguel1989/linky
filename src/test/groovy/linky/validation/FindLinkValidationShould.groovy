package linky.validation

import linky.command.link.FindLink
import linky.dao.LinkDao
import linky.exception.ValidationFailed
import spock.lang.Specification

class FindLinkValidationShould extends Specification {

	FindLinkValidation findLinkValidation
	LinkDao linkDao

	void setup() {
		linkDao = Mock(LinkDao)
		findLinkValidation = new FindLinkValidation(linkDao)
	}

	def 'null command'() {
		when:
		findLinkValidation.validate(null)

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Command can not be null'
	}

	def 'empty id'() {
		when:
		findLinkValidation.validate(new FindLink(''))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Link id is empty'
	}

	def 'empty id with spaces'() {
		when:
		findLinkValidation.validate(new FindLink('    '))

		then:
		def ex = thrown(IllegalArgumentException)
		ex.message == 'Invalid UUID string:     '
	}

	def 'not an UUID'() {
		when:
		findLinkValidation.validate(new FindLink('batmanID'))

		then:
		def ex = thrown(IllegalArgumentException)
		ex.message == 'Invalid UUID string: batmanID'
	}

	def 'link not found'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		linkDao.findOne(UUID.fromString(uuid)) >> null

		when:
		findLinkValidation.validate(new FindLink(uuid))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Link not found'
	}
}
