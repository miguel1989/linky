package linky.command;

import linky.dto.AuthUserBean;
import linky.infra.Command;

public class RegisterAdmin extends Register implements Command<AuthUserBean> {

	public RegisterAdmin(String email, String password, String name) {
		super(email, password, name);
	}

	@Override
	public String toLogString() {
		return "RegisterAdmin ( email=" + email + ", name=" + name + " )";
	}
}
