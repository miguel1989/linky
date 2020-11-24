package linky.command.link.admin;

import linky.command.BasePageSearchCommand;
import linky.dto.PageLinksBeanSimple;
import linky.infra.Command;

public class FindLinks extends BasePageSearchCommand implements Command<PageLinksBeanSimple> {

	public FindLinks(Integer page, Integer size, String sortField, String sortDirection, String search) {
		super(page, size, sortField, sortDirection, search);
	}

	@Override
	public String toLogString() {
		return "FindLinks{" +
				"page=" + page +
				", size=" + size +
				", sortField=" + sortField +
				", sortDirection=" + sortDirection +
				", search='" + search + '\'' +
				'}';
	}
}
