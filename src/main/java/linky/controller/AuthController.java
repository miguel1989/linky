package linky.controller;

import linky.command.RegisterUser;
import linky.dto.AuthUserBean;
import linky.dto.RegisterUserBean;
import linky.dto.UserBean;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@Autowired
	private PipedNow pipedNow;

	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public UserBean register(@RequestBody RegisterUserBean registerUserBean) {
		AuthUserBean authUserBean = new RegisterUser(
				registerUserBean.email,
				registerUserBean.password,
				registerUserBean.name).execute(pipedNow);

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(authUserBean.id, null, authUserBean.roles));

		return new UserBean(authUserBean);
	}
}
