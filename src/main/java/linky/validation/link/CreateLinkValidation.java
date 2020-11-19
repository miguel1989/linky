package linky.validation.link;

import com.google.common.base.Strings;
import linky.command.link.CreateLink;
import linky.dao.UserDao;
import linky.exception.ValidationFailed;
import linky.infra.Validation;
import linky.validation.object.AbuseLinkName;
import linky.validation.object.LinkName;
import linky.validation.object.LinkUrl;
import linky.validation.object.UniqueLinkName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateLinkValidation implements Validation<CreateLink> {

	private final UserDao userDao;
	private final AbuseLinkName abuseLinkName;
	private final UniqueLinkName uniqueLinkName;

	@Autowired
	public CreateLinkValidation(UserDao userDao,
								AbuseLinkName abuseLinkName,
								UniqueLinkName uniqueLinkName) {
		this.userDao = userDao;
		this.abuseLinkName = abuseLinkName;
		this.uniqueLinkName = uniqueLinkName;
	}

	@Override
	public void validate(CreateLink command) {
		if (Strings.isNullOrEmpty(command.userId())) {
			throw new ValidationFailed("UserId is empty");
		}

		if (!userDao.findById(UUID.fromString(command.userId())).isPresent()) {
			throw new ValidationFailed("User does not exist");
		}

		if (!new LinkName(command.name()).isValid()) {
			throw new ValidationFailed("Incorrect link name");
		}

		if (!abuseLinkName.isOk(command.name())) {
			throw new ValidationFailed("Incorrect link name");
		}

		if (!uniqueLinkName.guaranteed(command.name())) {
			throw new ValidationFailed("Link name is already taken");
		}

		if (!new LinkUrl(command.url()).isValid()) {
			throw new ValidationFailed("Incorrect link url");
		}
	}
}
