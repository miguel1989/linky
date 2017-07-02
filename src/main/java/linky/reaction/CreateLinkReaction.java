package linky.reaction;

import linky.command.CreateLink;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.infra.Command;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateLinkReaction implements Reaction<CreateLink, Command.R.Void> {

	@Autowired
	private LinkDao linkDao;

	@Override
	public Command.R.Void react(CreateLink command) {
		linkDao.save(new Link(command.name(), command.url(), command.userId()));
		return new Command.R.Void();
	}
}