package linky.validation.user;

import linky.command.user.RegisterAdmin;
import linky.infra.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterAdminValidation implements Validation<RegisterAdmin> {

	private final RegisterCommonValidation registerCommonValidation;

	@Autowired
	public RegisterAdminValidation(RegisterCommonValidation registerCommonValidation) {
		this.registerCommonValidation = registerCommonValidation;
	}

	@Override
	public void validate(RegisterAdmin command) {
		registerCommonValidation.validate(command);
		//todo maybe additional checks for admin user
	}
}
