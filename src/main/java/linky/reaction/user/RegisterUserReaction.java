package linky.reaction.user;

import linky.command.user.RegisterUser;
import linky.dao.UserDao;
import linky.domain.User;
import linky.dto.AuthUserBean;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserReaction implements Reaction<RegisterUser, AuthUserBean> {

	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public RegisterUserReaction(UserDao userDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public AuthUserBean react(RegisterUser command) {
		User user = new User(
				command.email(),
				passwordEncoder.encode(command.password()),
				command.name());
		userDao.save(user);
		return new AuthUserBean(user);
	}
}
