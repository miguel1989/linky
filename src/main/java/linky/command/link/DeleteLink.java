package linky.command.link;

import linky.dto.LinkBean;
import linky.infra.Command;

public class DeleteLink implements Command<LinkBean> {
	private String id;

	public DeleteLink(String id) {
		this.id = id;
	}

	public String id() {
		return this.id;
	}

	@Override
	public String toLogString() {
		return "DeleteLink(id = " + id + " )";
	}
}
