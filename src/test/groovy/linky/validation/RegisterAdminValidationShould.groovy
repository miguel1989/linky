package linky.validation

import linky.command.RegisterAdmin
import linky.dao.UserDao
import linky.domain.User
import linky.exception.ValidationFailed
import spock.lang.Specification

class RegisterAdminValidationShould extends Specification {

	RegisterAdminValidation registerAdminValidation
	UserDao userDao

	void setup() {
		userDao = Mock(UserDao)
		registerAdminValidation = new RegisterAdminValidation(userDao)
	}
	
	def 'null command'() {
		when:
		registerAdminValidation.validate(null)

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Command can not be null'
	}
	
	def 'null email'() {
		when:
		registerAdminValidation.validate(new RegisterAdmin(null, 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Email is empty'
	}

	def 'empty email'() {
		when:
		registerAdminValidation.validate(new RegisterAdmin('', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Email is empty'
	}

	def 'empty email with spaces'() {
		when:
		registerAdminValidation.validate(new RegisterAdmin('  ', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Email is empty'
	}

	def 'email exists'() {
		setup:
		userDao.findByEmail('admin@linky.lv') >> Optional.of(new User())
		
		when:
		registerAdminValidation.validate(new RegisterAdmin('admin@linky.lv', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Email already exists'
	}
	
	def 'not an email'() {
		setup:
		userDao.findByEmail('crap') >> Optional.of(new User())

		when:
		registerAdminValidation.validate(new RegisterAdmin('crap', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Not an email'
	}

	def 'null password'() {
		setup:
		userDao.findByEmail('admin@linky.lv') >> Optional.ofNullable(null)

		when:
		registerAdminValidation.validate(new RegisterAdmin('admin@linky.lv', null, 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Password is empty'
	}

	def 'empty password'() {
		setup:
		userDao.findByEmail('admin@linky.lv') >> Optional.ofNullable(null)

		when:
		registerAdminValidation.validate(new RegisterAdmin('admin@linky.lv', '', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Password is empty'
	}

	def 'empty password with spaces'() {
		setup:
		userDao.findByEmail('admin@linky.lv') >> Optional.ofNullable(null)

		when:
		registerAdminValidation.validate(new RegisterAdmin('admin@linky.lv', '   ', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Password is empty'
	}

	def 'password is too short'() {
		setup:
		userDao.findByEmail('admin@linky.lv') >> Optional.ofNullable(null)

		when:
		registerAdminValidation.validate(new RegisterAdmin('admin@linky.lv', 'short', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Password is too short'
	}

	def 'password is too simple'() {
		setup:
		userDao.findByEmail('admin@linky.lv') >> Optional.ofNullable(null)

		when:
		registerAdminValidation.validate(new RegisterAdmin('admin@linky.lv', 'qwertyasd', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'Password is too simple'
	}
	
	//todo msg "password already exists"!
}
