package linky.reaction;

import linky.command.link.DeleteLink;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.infra.Command;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DeleteLinkReaction implements Reaction<DeleteLink, Command.R.Void> {

	private final LinkDao linkDao;

	@Autowired
	public DeleteLinkReaction(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public Command.R.Void react(DeleteLink command) {
		Optional<Link> optLink = linkDao.findById(UUID.fromString(command.id()));
		optLink.ifPresent(linkDao::delete);
		return new Command.R.Void();
	}
}
