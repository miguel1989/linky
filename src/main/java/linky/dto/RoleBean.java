package linky.dto;

import org.springframework.security.core.GrantedAuthority;

public class RoleBean implements GrantedAuthority {
	public String value;
	
	RoleBean (String value) {
		this.value = value;
	}

	@Override
	public String getAuthority() {
		return value;
	}
}
