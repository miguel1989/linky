package linky.command.link;

import linky.dto.LinkBean;
import linky.infra.Command;

public class CreateLink implements Command<LinkBean> {

	private final String userId;
	private final String name;
	private final String url;

	public CreateLink(String userId, String name, String url) {
		this.userId = userId;
		this.name = name;
		this.url = url;
	}

	public String userId() {
		return userId;
	}
	public String name() {
		return name;
	}
	public String url() {
		return url;
	}

	@Override
	public String toLogString() {
		return "CreateLink ( userId=" + userId + ", name=" + name + ", url=" + url + " )";
	}
}
