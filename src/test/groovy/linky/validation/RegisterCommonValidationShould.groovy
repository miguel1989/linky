package linky.validation

import linky.command.user.RegisterAdmin
import linky.command.user.RegisterUser
import linky.dao.UserDao
import linky.domain.User
import linky.exception.ValidationFailed
import spock.lang.Ignore
import spock.lang.Specification

class RegisterCommonValidationShould extends Specification {

	RegisterCommonValidation registerCommonValidation
	UserDao userDao

	void setup() {
		userDao = Mock(UserDao)
		registerCommonValidation = new RegisterCommonValidation(userDao)
	}

	def 'null email'() {
		when:
		registerCommonValidation.validate(new RegisterUser(null, 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Not a valid e-mail'
	}

	def 'empty email'() {
		when:
		registerCommonValidation.validate(new RegisterUser('', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Not a valid e-mail'
	}

	def 'empty email with spaces'() {
		when:
		registerCommonValidation.validate(new RegisterUser('  ', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Not a valid e-mail'
	}

	def 'email exists'() {
		setup:
		userDao.findByEmail('test@linky.lv') >> Optional.of(new User())

		when:
		registerCommonValidation.validate(new RegisterUser('test@linky.lv', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'E-mail already exists'
	}

	def 'not an email'() {
		setup:
		userDao.findByEmail('crap') >> Optional.empty()

		when:
		registerCommonValidation.validate(new RegisterUser('crap', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Not a valid e-mail'
	}

	def 'null password'() {
		setup:
		userDao.findByEmail('test@linky.lv') >> Optional.ofNullable(null)

		when:
		registerCommonValidation.validate(new RegisterUser('test@linky.lv', null, 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Password is empty'
	}

	def 'empty password'() {
		setup:
		userDao.findByEmail('test@linky.lv') >> Optional.ofNullable(null)

		when:
		registerCommonValidation.validate(new RegisterUser('test@linky.lv', '', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Password is empty'
	}

	def 'empty password with spaces'() {
		setup:
		userDao.findByEmail('test@linky.lv') >> Optional.ofNullable(null)

		when:
		registerCommonValidation.validate(new RegisterUser('test@linky.lv', '   ', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Password is empty'
	}

	@Ignore
	def 'password is too short'() {
		setup:
		userDao.findByEmail('admin@linky.lv') >> Optional.ofNullable(null)

		when:
		registerCommonValidation.validate(new RegisterAdmin('admin@linky.lv', 'short', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Password is too short'
	}

	@Ignore
	def 'password is too simple'() {
		setup:
		userDao.findByEmail('admin@linky.lv') >> Optional.ofNullable(null)

		when:
		registerCommonValidation.validate(new RegisterAdmin('admin@linky.lv', 'qwertyasd', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Password is too simple'
	}

}
