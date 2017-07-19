package linky.command;

public class AbstractRegister {

	protected String email;
	protected String password;
	protected String name;

	AbstractRegister(String email, String password, String name) {
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
