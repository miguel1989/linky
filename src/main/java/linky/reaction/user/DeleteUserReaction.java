package linky.reaction.user;

import linky.command.user.DeleteUser;
import linky.dao.LinkDao;
import linky.dao.UserDao;
import linky.domain.Link;
import linky.domain.User;
import linky.infra.Command;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DeleteUserReaction implements Reaction<DeleteUser, Command.R.Void> {

	private final UserDao userDao;
	private final LinkDao linkDao;

	@Autowired
	public DeleteUserReaction(UserDao userDao, LinkDao linkDao) {
		this.userDao = userDao;
		this.linkDao = linkDao;
	}

	@Override
	public Command.R.Void react(DeleteUser command) {
		Optional<User> optionalUser = userDao.findByEmail(command.email());
		optionalUser.ifPresent(user -> {
			List<Link> links = linkDao.findByCreatedBy(user.id().toString());
			linkDao.deleteAll(links);
			userDao.delete(user);
		});
		return new Command.R.Void();
	}
}
