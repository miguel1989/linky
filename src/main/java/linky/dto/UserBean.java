package linky.dto;

import linky.domain.User;

public class UserBean {
	public String id;
	public String email;
	public String name;
	
	public UserBean (User user) {
		id = user.id().toString();
		email = user.email();
		name = user.name();
	}
}
