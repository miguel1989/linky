package linky.validation

import linky.command.link.CreateLink
import linky.dao.UserDao
import linky.domain.User
import linky.exception.ValidationFailed
import linky.validation.object.AbuseLinkName
import linky.validation.object.UniqueLinkName
import spock.lang.Specification

class CreateLinkValidationShould extends Specification {

	CreateLinkValidation createLinkValidation
	UserDao userDao
	AbuseLinkName abuseLinkName
	UniqueLinkName uniqueLinkName

	void setup() {
		uniqueLinkName = Mock(UniqueLinkName)
		abuseLinkName = Mock(AbuseLinkName)
		userDao = Mock(UserDao)
		createLinkValidation = new CreateLinkValidation(userDao, abuseLinkName, uniqueLinkName)
	}

	def 'null command'() {
		when:
		createLinkValidation.validate(null)

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Command can not be null'
	}

	def 'null user id'() {
		when:
		createLinkValidation.validate(new CreateLink(null, 'gogle', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'UserId is empty'
	}

	def 'empty user id'() {
		when:
		createLinkValidation.validate(new CreateLink('', 'gogle', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'UserId is empty'
	}

	def 'not a UUID string'() {
		when:
		createLinkValidation.validate(new CreateLink('batman', 'gogle', 'www.gogle.lv'))

		then:
		def ex = thrown(IllegalArgumentException)
		ex.message == 'Invalid UUID string: batman'
	}

	def 'user does not exist'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		userDao.findOne(UUID.fromString(uuid)) >> null

		when:
		createLinkValidation.validate(new CreateLink(uuid, 'gogle', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'User does not exist'
	}

	def 'empty name'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		userDao.findOne(UUID.fromString(uuid)) >> new User()

		when:
		createLinkValidation.validate(new CreateLink(uuid, '', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Incorrect link name'
	}

	def 'restricted chars'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		userDao.findOne(UUID.fromString(uuid)) >> new User()

		when:
		createLinkValidation.validate(new CreateLink(uuid, 'abc/qwe!', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Incorrect link name'
	}

	def 'abuse'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		userDao.findOne(UUID.fromString(uuid)) >> new User()
		abuseLinkName.isOk('niger') >> false

		when:
		createLinkValidation.validate(new CreateLink(uuid, 'niger', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Incorrect link name'
	}

	def 'not unique name'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		userDao.findOne(UUID.fromString(uuid)) >> new User()
		abuseLinkName.isOk('google') >> true
		uniqueLinkName.guaranteed('google') >> false

		when:
		createLinkValidation.validate(new CreateLink(uuid, 'google', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Link name is already taken'
	}
}
