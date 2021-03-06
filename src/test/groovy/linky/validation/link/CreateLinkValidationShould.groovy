package linky.validation.link

import linky.command.link.CreateLink
import linky.dao.UserDao
import linky.domain.User
import linky.exception.ValidationFailed
import linky.validation.link.CreateLinkValidation
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
		userDao.findById(UUID.fromString(uuid)) >> Optional.empty()

		when:
		createLinkValidation.validate(new CreateLink(uuid, 'gogle', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'User does not exist'
	}

	def 'empty name'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		userDao.findById(UUID.fromString(uuid)) >> Optional.of(new User())

		when:
		createLinkValidation.validate(new CreateLink(uuid, '', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Incorrect link name'
	}

	def 'restricted chars'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		userDao.findById(UUID.fromString(uuid)) >> Optional.of(new User())

		when:
		createLinkValidation.validate(new CreateLink(uuid, 'abc/qwe!', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Incorrect link name'
	}

	def 'abuse'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		userDao.findById(UUID.fromString(uuid)) >> Optional.of(new User())
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
		CreateLink command = new CreateLink(uuid, "google", "gogle.lv")
		userDao.findById(UUID.fromString(uuid)) >> Optional.of(new User())
		abuseLinkName.isOk(command.name()) >> true
		uniqueLinkName.guaranteed(command.name()) >> false

		when:
		createLinkValidation.validate(command)

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Link name is already taken'
	}

	def 'not an url'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		CreateLink command = new CreateLink(uuid, "google", "som#!@ething%^&*")
		userDao.findById(UUID.fromString(uuid)) >> Optional.of(new User())
		abuseLinkName.isOk(command.name()) >> true
		uniqueLinkName.guaranteed(command.name()) >> true

		when:
		createLinkValidation.validate(command)

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Incorrect link url'
	}

	def 'correct command no protocol in url'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		CreateLink command = new CreateLink(uuid, "google", "www.gogle.lv")
		userDao.findById(UUID.fromString(uuid)) >> Optional.of(new User())
		abuseLinkName.isOk(command.name()) >> true
		uniqueLinkName.guaranteed(command.name()) >> true

		when:
		createLinkValidation.validate(command)

		then:
		noExceptionThrown()
	}

	def 'correct command ftp protocol'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		CreateLink command = new CreateLink(uuid, "google", "ftp://www.gogle.lv")
		userDao.findById(UUID.fromString(uuid)) >> Optional.of(new User())
		abuseLinkName.isOk(command.name()) >> true
		uniqueLinkName.guaranteed(command.name()) >> true

		when:
		createLinkValidation.validate(command)

		then:
		noExceptionThrown()
	}

	def 'correct command https protocol'() {
		setup:
		String uuid = UUID.randomUUID().toString()
		CreateLink command = new CreateLink(uuid, "google", "https://www.gogle.lv")
		userDao.findById(UUID.fromString(uuid)) >> Optional.of(new User())
		abuseLinkName.isOk(command.name()) >> true
		uniqueLinkName.guaranteed(command.name()) >> true

		when:
		createLinkValidation.validate(command)

		then:
		noExceptionThrown()
	}
}
