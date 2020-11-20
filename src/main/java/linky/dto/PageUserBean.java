package linky.dto;

import linky.infra.Command;

public class PageUserBean implements Command.R {

	public RestResponsePage<UserBean> userBeans;

	public PageUserBean() {

	}

	public PageUserBean(RestResponsePage<UserBean> userBeans) {
		this.userBeans = userBeans;
	}
}
