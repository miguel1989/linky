package linky.validation

import linky.command.RegisterUser
import linky.dao.UserDao
import linky.domain.User
import linky.exception.ValidationFailed
import spock.lang.Specification

class RegisterUserValidationShould extends Specification {

	RegisterUserValidation registerUserValidation
	UserDao userDao

	void setup() {
		userDao = Mock(UserDao)
		registerUserValidation = new RegisterUserValidation(userDao)
	}
	
	def 'null command'() {
		when:
		registerUserValidation.validate(null)

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Command can not be null'
	}
	
	def 'null email'() {
		when:
		registerUserValidation.validate(new RegisterUser(null, 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Email is empty'
	}

	def 'empty email'() {
		when:
		registerUserValidation.validate(new RegisterUser('', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Email is empty'
	}

	def 'empty email with spaces'() {
		when:
		registerUserValidation.validate(new RegisterUser('  ', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Email is empty'
	}

	def 'email exists'() {
		setup:
		userDao.findByEmail('test@linky.lv') >> Optional.of(new User())
		
		when:
		registerUserValidation.validate(new RegisterUser('test@linky.lv', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Email already exists'
	}

	def 'not an email'() {
		setup:
		userDao.findByEmail('crap') >> Optional.of(new User())

		when:
		registerUserValidation.validate(new RegisterUser('crap', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Not an email'
	}

	def 'null password'() {
		setup:
		userDao.findByEmail('test@linky.lv') >> Optional.ofNullable(null)

		when:
		registerUserValidation.validate(new RegisterUser('test@linky.lv', null, 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Password is empty'
	}

	def 'empty password'() {
		setup:
		userDao.findByEmail('test@linky.lv') >> Optional.ofNullable(null)

		when:
		registerUserValidation.validate(new RegisterUser('test@linky.lv', '', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Password is empty'
	}

	def 'empty password with spaces'() {
		setup:
		userDao.findByEmail('test@linky.lv') >> Optional.ofNullable(null)

		when:
		registerUserValidation.validate(new RegisterUser('test@linky.lv', '   ', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Password is empty'
	}
	
	//todo msg "password already exists"!
}
