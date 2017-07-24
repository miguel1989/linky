package linky.dto;

public class RegisterUserBean {
	public String email;
	public String password;
	public String name;
	
	public RegisterUserBean() {
		
	}
	
	public RegisterUserBean(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}
}
