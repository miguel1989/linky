package linky.command.user;

public abstract class Register {

	protected String email;
	protected String password;
	protected String name;

	Register(String email, String password, String name) {
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
}
