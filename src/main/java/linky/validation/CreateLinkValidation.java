package linky.validation;

import com.google.common.base.Strings;
import linky.command.CreateLink;
import linky.dao.LinkDao;
import linky.dao.UserDao;
import linky.exception.ValidationFailed;
import linky.infra.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CreateLinkValidation implements Validation<CreateLink> {

	private final UserDao userDao;
	private final LinkDao linkDao;

	@Value("#{'${reserved.words}'.split(',')}")
	private List<String> reservedWords;
	@Value("#{'${abuse.words}'.split(',')}")
	private List<String> abuseWords;

	@Autowired
	public CreateLinkValidation(UserDao userDao, LinkDao linkDao) {
		this.userDao = userDao;
		this.linkDao = linkDao;
	}

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
		
		//todo maybe create self validated object Name
		if (Strings.isNullOrEmpty(command.name())) {
			throw new ValidationFailed("Name is empty");
		}

		if (linkDao.findByName(command.name()).isPresent()) {
			throw new ValidationFailed("Name is already taken");
		}
		
		if (isRestricted(command.name())) {
			throw new ValidationFailed("Wrong name");
		}
	}

	private boolean isRestricted(String name) {
		String lower = name.toLowerCase();
		return reservedWords.stream().filter(lower::equals).count() > 0 
				|| abuseWords.stream().filter(lower::contains).count() > 0;
	}
}
