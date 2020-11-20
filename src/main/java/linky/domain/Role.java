package linky.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

	static final String ROLE_USER = "ROLE_USER";
	static final String ROLE_ADMIN = "ROLE_ADMIN";

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "role", length = 120)
	private String role;

	//default constructor for hibernate
	public Role() {

	}

	Role(User user, String role) {
		this.user = user;
		this.role = role;
	}

	public User user() {
		return this.user;
	}

	@Override
	public String getAuthority() {
		return role;
	}
}
