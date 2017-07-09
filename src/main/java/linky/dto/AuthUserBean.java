package linky.dto;

import linky.domain.User;
import linky.infra.Command;

import java.util.ArrayList;
import java.util.Collection;

public class AuthUserBean implements Command.R {
	public String id;
	public String email;
	public String name;
	public Collection<RoleBean> roles;

	public AuthUserBean (User user) {
		id = user.id().toString();
		email = user.email();
		name = user.name();
		roles = new ArrayList<>();
		user.getAuthorities().forEach(role -> roles.add(new RoleBean(role.getAuthority())));
	}
}
