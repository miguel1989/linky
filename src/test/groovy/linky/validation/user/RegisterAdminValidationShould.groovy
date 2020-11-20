package linky.validation.user

import linky.command.user.RegisterAdmin
import linky.exception.ValidationFailed
import linky.validation.user.RegisterAdminValidation
import linky.validation.user.RegisterCommonValidation
import spock.lang.Specification

class RegisterAdminValidationShould extends Specification {

	RegisterAdminValidation registerAdminValidation
	RegisterCommonValidation registerCommonValidation

	void setup() {
		registerCommonValidation = Mock(RegisterCommonValidation)
		registerAdminValidation = new RegisterAdminValidation(registerCommonValidation)
	}

	def 'exception occurred'() {
		setup:
		registerCommonValidation.validate(_) >> { throw new ValidationFailed('something wrong') }

		when:
		registerAdminValidation.validate(new RegisterAdmin('test@linky.lv', 'secret', 'batman'))

		then:
		def ex = thrown(ValidationFailed)
		ex.message == 'something wrong'
	}
}
