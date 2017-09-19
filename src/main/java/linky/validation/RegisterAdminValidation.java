package linky.validation;

import linky.command.user.RegisterAdmin;
import linky.dao.UserDao;
import linky.exception.ValidationFailed;
import linky.infra.Validation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterAdminValidation implements Validation<RegisterAdmin> {

	private final UserDao userDao;

	@Autowired
	public RegisterAdminValidation(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void validate(RegisterAdmin command) {
		if (command == null) {
			throw new ValidationFailed("Command can not be null");
		}

		//todo maybe create self validated object EMAIL
		if (StringUtils.isBlank(command.email())) {
			throw new ValidationFailed("Email is empty");
		}

		if (userDao.findByEmail(command.email()).isPresent()) {
			throw new ValidationFailed("Email already exists");
		}

		//todo maybe create self validated object PASSWORD
		if (StringUtils.isBlank(command.password())) {
			throw new ValidationFailed("Password is empty");
		}
	}
}
