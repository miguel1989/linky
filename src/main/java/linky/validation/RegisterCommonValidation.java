package linky.validation;

import linky.command.user.Register;
import linky.dao.UserDao;
import linky.exception.ValidationFailed;
import linky.validation.object.Email;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterCommonValidation {

	private final UserDao userDao;

	@Autowired
	public RegisterCommonValidation(UserDao userDao) {
		this.userDao = userDao;
	}

	public void validate(Register command) {
		if (command == null) {
			throw new ValidationFailed("Command can not be null");
		}

		if (StringUtils.isBlank(command.email())) {
			throw new ValidationFailed("Not a valid e-mail");
		}
		
		if (!new Email(command.email()).isValid()) {
			throw new ValidationFailed("Not a valid e-mail");
		}
		
		if (userDao.findByEmail(command.email()).isPresent()) {
			throw new ValidationFailed("E-mail already exists");
		}

		//todo maybe create self validated object PASSWORD
		if (StringUtils.isBlank(command.password())) {
			throw new ValidationFailed("Password is empty");
		}
	}
}
