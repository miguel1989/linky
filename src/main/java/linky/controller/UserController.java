package linky.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@RequestMapping("/me")
	public String me() {
		return AuthUser.id();
	}
}
