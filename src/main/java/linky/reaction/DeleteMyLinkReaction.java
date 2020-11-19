package linky.reaction;

import linky.command.link.DeleteMyLink;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.infra.Command;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DeleteMyLinkReaction implements Reaction<DeleteMyLink, Command.R.Void> {

	private final LinkDao linkDao;

	@Autowired
	public DeleteMyLinkReaction(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public Command.R.Void react(DeleteMyLink command) {
		Optional<Link> optLink = linkDao.findById(UUID.fromString(command.id()));
		optLink.ifPresent(linkDao::delete);
		return new Command.R.Void();
	}
}
