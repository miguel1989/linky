package linky.command.link;

import linky.command.BasePageCommand;
import linky.dto.PageLinksBeanSimple;
import linky.infra.Command;

public class FindLinksForUser extends BasePageCommand implements Command<PageLinksBeanSimple> {

	private final String userId;

	//todo maybe add searchStr
	public FindLinksForUser(String userId, Integer page, Integer size, String sortField, String sortDirection) {
		super(page, size, sortField, sortDirection);
		this.userId = userId;
	}

	public String userId() {
		return userId;
	}

	@Override
	public String toLogString() {
		return "FindLinksForUser{" +
				"userId=" + userId +
				", page=" + page +
				", size=" + size +
				", sortField=" + sortField +
				", sortDirection=" + sortDirection +
				'}';
	}
}
