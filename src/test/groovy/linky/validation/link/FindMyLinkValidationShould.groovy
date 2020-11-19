package linky.validation.link

import linky.command.link.FindMyLink
import linky.dao.LinkDao
import linky.domain.Link
import linky.exception.ValidationFailed
import linky.validation.link.FindMyLinkValidation
import spock.lang.Specification

class FindMyLinkValidationShould extends Specification {

	FindMyLinkValidation findLinkValidation
	LinkDao linkDao

	void setup() {
		linkDao = Mock(LinkDao)
		findLinkValidation = new FindMyLinkValidation(linkDao)
	}

	def 'empty id'() {
		when:
		findLinkValidation.validate(new FindMyLink('', ''))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Link id is empty'
	}

	def 'empty id with spaces'() {
		when:
		findLinkValidation.validate(new FindMyLink('    ', ''))

		then:
		def ex = thrown(IllegalArgumentException)
		ex.message == 'Invalid UUID string:     '
	}

	def 'not an UUID'() {
		when:
		findLinkValidation.validate(new FindMyLink('batmanID', ''))

		then:
		def ex = thrown(IllegalArgumentException)
		ex.message == 'Invalid UUID string: batmanID'
	}

	def 'link not found'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		linkDao.findById(UUID.fromString(uuid)) >> Optional.empty()

		when:
		findLinkValidation.validate(new FindMyLink(uuid, ''))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Link not found'
	}

	def 'wrong user to read my link'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		linkDao.findById(UUID.fromString(uuid)) >> Optional.of(new Link('myName', 'myUrl', 'batman'))

		when:
		findLinkValidation.validate(new FindMyLink(uuid, 'superman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'You are not allowed to read this link'
	}

	def 'everything is ok'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		linkDao.findById(UUID.fromString(uuid)) >> Optional.of(new Link('myName', 'myUrl', 'batman'))

		when:
		findLinkValidation.validate(new FindMyLink(uuid, 'batman'))

		then:
		noExceptionThrown()
	}
}
