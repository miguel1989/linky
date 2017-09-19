package linky.command.user;

import linky.dto.AuthUserBean;
import linky.infra.Command;

public class RegisterUser extends Register implements Command<AuthUserBean> {

	public RegisterUser(String email, String password, String name) {
		super(email, password, name);
	}

	@Override
	public String toLogString() {
		return "RegisterUser ( email=" + email + ", name=" + name + " )";
	}
}
