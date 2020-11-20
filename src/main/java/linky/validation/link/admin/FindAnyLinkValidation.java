package linky.validation.link.admin;

import com.google.common.base.Strings;
import linky.command.link.admin.FindAnyLink;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.exception.ValidationFailed;
import linky.infra.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FindAnyLinkValidation implements Validation<FindAnyLink> {

	private final LinkDao linkDao;

	@Autowired
	public FindAnyLinkValidation(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public void validate(FindAnyLink command) {
		if (Strings.isNullOrEmpty(command.id())) {
			throw new ValidationFailed("Link id is empty");
		}

		Optional<Link> optLink = linkDao.findById(UUID.fromString(command.id()));
		if (!optLink.isPresent()) {
			throw new ValidationFailed("Link not found");
		}
	}
}
