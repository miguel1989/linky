package linky.validation.user

import linky.command.user.RegisterUser
import linky.exception.ValidationFailed
import linky.validation.user.RegisterCommonValidation
import linky.validation.user.RegisterUserValidation
import spock.lang.Specification

class RegisterUserValidationShould extends Specification {

	RegisterUserValidation registerUserValidation
	RegisterCommonValidation registerCommonValidation

	void setup() {
		registerCommonValidation = Mock(RegisterCommonValidation)
		registerUserValidation = new RegisterUserValidation(registerCommonValidation)
	}

	def 'exception occurred'() {
		setup:
		registerCommonValidation.validate(_) >> { throw new ValidationFailed('something wrong') }

		when:
		registerUserValidation.validate(new RegisterUser('test@linky.lv', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'something wrong'
	}
}
