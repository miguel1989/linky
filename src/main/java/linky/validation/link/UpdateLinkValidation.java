package linky.validation.link;

import com.google.common.base.Strings;
import linky.command.link.CreateLink;
import linky.command.link.UpdateLink;
import linky.dao.LinkDao;
import linky.dao.UserDao;
import linky.domain.Link;
import linky.exception.ValidationFailed;
import linky.infra.Validation;
import linky.validation.object.AbuseLinkName;
import linky.validation.object.LinkName;
import linky.validation.object.LinkUrl;
import linky.validation.object.UniqueLinkName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UpdateLinkValidation implements Validation<UpdateLink> {

	private final CreateLinkValidation createLinkValidation;
	private final LinkDao linkDao;

	@Autowired
	public UpdateLinkValidation(CreateLinkValidation createLinkValidation, LinkDao linkDao) {
		this.createLinkValidation = createLinkValidation;
		this.linkDao = linkDao;
	}

	@Override
	public void validate(UpdateLink command) {
		createLinkValidation.validate(command);

		Optional<Link> optionalLink = linkDao.findById(UUID.fromString(command.linkId()));
		if (!optionalLink.isPresent()) {
			throw new ValidationFailed("Link does not exist");
		}

		Link link = optionalLink.get();
		if (!link.createdBy().equals(command.userId())) {
			throw new ValidationFailed("You are not allowed to edit this link");
		}
	}
}
