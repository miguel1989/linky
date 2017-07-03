package linky.validation;

import com.google.common.base.Strings;
import linky.command.CreateLink;
import linky.dao.LinkDao;
import linky.dao.UserDao;
import linky.exception.ValidationFailed;
import linky.infra.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateLinkValidation implements Validation<CreateLink> {

	@Autowired
	private UserDao userDao;

	@Autowired
	private LinkDao linkDao;

	@Override
	public void validate(CreateLink command) {
		if (command == null) {
			throw new ValidationFailed("Command can not be null");
		}

		if (Strings.isNullOrEmpty(command.userId())) {
			throw new ValidationFailed("UserId is empty");
		}

		if (userDao.findOne(UUID.fromString(command.userId())) == null) {
			throw new ValidationFailed("User does not exist");
		}

		if (Strings.isNullOrEmpty(command.name())) {
			throw new ValidationFailed("Name is empty");
		}
		
		if (linkDao.findByName(command.name()).isPresent()) {
			throw new ValidationFailed("Name is already taken");
		}
	}
}
