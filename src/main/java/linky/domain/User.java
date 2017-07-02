package linky.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@Column(name = "email", unique = true, length = 120)
	private String email;//todo maybe Email obj with custom validations

	@Column(name = "password") //already encoded
	private String password; //todo maybe Password(char[]) obj with custom validations

	@Column(name = "name")
	private String name;

	//default constructor for hibernate
	public User() {

	}

	public User(String email, String password, String name) {
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
