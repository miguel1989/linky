package linky.validation;

import linky.command.user.RegisterUser;
import linky.infra.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserValidation implements Validation<RegisterUser> {

	private final RegisterCommonValidation registerCommonValidation;

	@Autowired
	public RegisterUserValidation(RegisterCommonValidation registerCommonValidation) {
		this.registerCommonValidation = registerCommonValidation;
	}

	@Override
	public void validate(RegisterUser command) {
		registerCommonValidation.validate(command);
		//todo maybe additional checks for mortal user
	}
}
