package linky.command;

import linky.dto.AuthUserBean;
import linky.infra.Command;

public class RegisterUser implements Command<AuthUserBean> {

	private String email;
	private String password; //char[]
	private String name;
	
	public RegisterUser(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}
	
	public String email() {
		return email;
	}
	public String password() {
		return password;
	}
	public String name() {
		return name;
	}

	@Override
	public String toLogString() {
		return "RegisterUser ( email=" + email + ", name=" + name + " )";
	}
}
