package linky.controller;

import linky.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

	@RequestMapping("/me")
	public String me() {
		return getLoggedInUserId();
	}

	private String getLoggedInUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null
				&& auth instanceof UsernamePasswordAuthenticationToken
				&& auth.getPrincipal() != null) {
			//from 'login' spring sets User
			if (auth.getPrincipal() instanceof User) {
				return ((User)auth.getPrincipal()).id().toString();
			}
			//from 'register' i set the ID it self already
			return auth.getPrincipal().toString();
		}
		//todo maybe throw exception
		return null;
	}
}
