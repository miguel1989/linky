package linky.validation;

import linky.command.RegisterUser;
import linky.dao.UserDao;
import linky.exception.ValidationFailed;
import linky.infra.Validation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserValidation implements Validation<RegisterUser> {

	@Autowired
	private UserDao userDao;

	@Override
	public void validate(RegisterUser command) {
		if (command == null) {
			throw new ValidationFailed("Command can not be null");
		}

		if (StringUtils.isBlank(command.email())) {
			throw new ValidationFailed("Email is empty");
		}

		if (userDao.findByEmail(command.email()).isPresent()) {
			throw new ValidationFailed("Email already exists");
		}

		if (StringUtils.isBlank(command.password())) {
			throw new ValidationFailed("Password is empty");
		}
	}
}
