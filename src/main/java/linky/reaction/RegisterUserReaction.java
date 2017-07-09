package linky.reaction;

import linky.command.RegisterUser;
import linky.dao.UserDao;
import linky.domain.User;
import linky.dto.AuthUserBean;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserReaction implements Reaction<RegisterUser, AuthUserBean> {

	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
