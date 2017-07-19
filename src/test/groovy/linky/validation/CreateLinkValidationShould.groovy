package linky.validation

import linky.command.CreateLink
import linky.dao.LinkDao
import linky.dao.UserDao
import linky.domain.Link
import linky.domain.User
import linky.exception.ValidationFailed
import spock.lang.Specification

class CreateLinkValidationShould extends Specification {

	CreateLinkValidation createLinkValidation
	LinkDao linkDao
	UserDao userDao

	void setup() {
		linkDao = Mock(LinkDao)
		userDao = Mock(UserDao)
		createLinkValidation = new CreateLinkValidation(userDao, linkDao)
		createLinkValidation.reservedWords = ['login', 'register']
		createLinkValidation.abuseWords = ['niger']
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
		createLinkValidation.validate(new CreateLink('', 'gogle', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'UserId is empty'
	}

	def "not a UUID string"() {
		when:
		createLinkValidation.validate(new CreateLink('batman', 'gogle', 'www.gogle.lv'))

		then:
		def ex = thrown(IllegalArgumentException)
		ex.message == 'Invalid UUID string: batman'
	}

	def "user does not exist"() {
		setup:
		String uuid = UUID.randomUUID().toString()
		userDao.findOne(UUID.fromString(uuid)) >> null

		when:
		createLinkValidation.validate(new CreateLink(uuid, 'gogle', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'User does not exist'
	}

	def "null name"() {
		setup:
		String uuid = UUID.randomUUID().toString()
		userDao.findOne(UUID.fromString(uuid)) >> new User()

		when:
		createLinkValidation.validate(new CreateLink(uuid, null, 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Name is empty'
	}

	def "empty name"() {
		setup:
		String uuid = UUID.randomUUID().toString()
		userDao.findOne(UUID.fromString(uuid)) >> new User()

		when:
		createLinkValidation.validate(new CreateLink(uuid, '', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Name is empty'
	}

	def "not unique name"() {
		setup:
		String uuid = UUID.randomUUID().toString()
		userDao.findOne(UUID.fromString(uuid)) >> new User()
		linkDao.findByName('google') >> Optional.of(new Link())

		when:
		createLinkValidation.validate(new CreateLink(uuid, 'google', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Name is already taken'
	}

	def "is restricted name"() {
		expect:
		createLinkValidation.isRestricted(name) == result

		where:
		name         | result
		'xloginx'    | false //reserved words, but no full match
		'loginx'     | false //reserved words, but no full match
		'registerMe' | false //reserved words, but no full match
		'REGISTERme' | false //reserved words, but no full match
		'john'       | false //normal
		'login'      | true //reserved words
		'LOGIN'      | true //reserved words
		'register'   | true //reserved words
		'REGISTER'   | true //reserved words
		'niger'      | true //abuse words
		'NIGER'      | true //abuse words
		'XNIGERX'    | true //abuse words
		'xnigerx'    | true //abuse words
		'snigerzz'   | true //abuse words
	}

	def "wrong name"() {
		setup:
		String uuid = UUID.randomUUID().toString()
		userDao.findOne(UUID.fromString(uuid)) >> new User()
		linkDao.findByName('login') >> Optional.empty()

		when:
		createLinkValidation.validate(new CreateLink(uuid, 'login', 'www.gogle.lv'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Wrong name'
	}
}
