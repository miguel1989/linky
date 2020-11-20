package linky.dto;

import linky.domain.User;
import linky.infra.Command;

public class UserBean implements Command.R {
	public String id;
	public String email;
	public String name;

	public UserBean() {

	}

	public UserBean(User user) {
		id = user.id().toString();
		email = user.email();
		name = user.name();
	}

	public UserBean(AuthUserBean authUserBean) {
		id = authUserBean.id;
		email = authUserBean.email;
		name = authUserBean.name;
	}
}
