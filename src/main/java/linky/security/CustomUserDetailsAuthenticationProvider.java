package linky.security;

import linky.dao.UserDao;
import linky.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
		if (usernamePasswordAuthenticationToken.getCredentials() == null) {
			throw new BadCredentialsException("no password");
		}
		String rawPass = usernamePasswordAuthenticationToken.getCredentials().toString();
		if (!passwordEncoder.matches(rawPass, userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password");
		}
	}

	@Override
	protected UserDetails retrieveUser(String email, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
		Optional<User> optionalUser = userDao.findByEmail(email);
		if (!optionalUser.isPresent()) {
			throw new UsernameNotFoundException("something is wrong");
		}
		return optionalUser.get();
	}
}
