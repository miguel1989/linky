package linky.validation;

import com.google.common.base.Strings;
import linky.command.link.DeleteMyLink;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.exception.ValidationFailed;
import linky.infra.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DeleteMyLinkValidation implements Validation<DeleteMyLink> {
	private final LinkDao linkDao;

	@Autowired
	public DeleteMyLinkValidation(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public void validate(DeleteMyLink command) {
		if (Strings.isNullOrEmpty(command.id())) {
			throw new ValidationFailed("Link id is empty");
		}

		Optional<Link> optLink = linkDao.findById(UUID.fromString(command.id()));
		if (!optLink.isPresent()) {
			throw new ValidationFailed("Link not found");
		}

		if (!optLink.get().createdBy().equals(command.requestUserId())) {
			throw new ValidationFailed("You are not allowed to delete this link");
		}
	}
}
