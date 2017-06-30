package linky.domain;

import com.datastax.driver.core.utils.UUIDs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalTime;
import java.util.UUID;

@Entity
public class User {

	@Id
	@Column(name = "id")
	private UUID id = UUIDs.timeBased();

	@Column(name = "email", unique = true, length = 120)
	private String email;//todo maybe Email obj with custom validations

	@Column(name = "password") //already encoded
	private String password; //todo maybe Password(char[]) obj with custom validations

	@Column(name = "name")
	private String name;

	@Column(name = "created_at")
	private LocalTime createdAt = LocalTime.now();

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

	public UUID id() {
		return id;
	}

	public LocalTime createdAt() {
		return createdAt;
	}
}
