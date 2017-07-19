package linky.reaction;

import linky.command.RegisterAdmin;
import linky.dao.UserDao;
import linky.domain.User;
import linky.dto.AuthUserBean;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterAdminReaction implements Reaction<RegisterAdmin, AuthUserBean> {

	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public RegisterAdminReaction(UserDao userDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public AuthUserBean react(RegisterAdmin command) {
		User user = new User(
				command.email(),
				passwordEncoder.encode(command.password()),
				command.name());
		user.grantAdmin(); //todo maybe delete role_user for admin?
		userDao.save(user);
		return new AuthUserBean(user);
	}
}
