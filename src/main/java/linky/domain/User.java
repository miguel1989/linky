package linky.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

	@Column(name = "email", unique = true, length = 120)
	private String email;//todo maybe Email obj with custom validations

	@Column(name = "password") //already encoded
	private String password; //todo maybe Password(char[]) obj with custom validations

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Role> roles = new ArrayList<>();

	//default constructor for hibernate
	public User() {

	}

	public User(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.roles.add(new Role(this, "USER"));
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
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
