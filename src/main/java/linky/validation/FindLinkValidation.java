package linky.validation;

import com.google.common.base.Strings;
import linky.command.link.FindLink;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.exception.ValidationFailed;
import linky.infra.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FindLinkValidation implements Validation<FindLink> {

	private final LinkDao linkDao;

	@Autowired
	public FindLinkValidation(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public void validate(FindLink command) {
		if (Strings.isNullOrEmpty(command.id())) {
			throw new ValidationFailed("Link id is empty");
		}

		Optional<Link> optLink = linkDao.findById(UUID.fromString(command.id()));
		if (!optLink.isPresent()) {
			throw new ValidationFailed("Link not found");
		}
	}
}
