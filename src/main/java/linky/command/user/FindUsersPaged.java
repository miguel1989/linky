package linky.command.user;

import linky.command.BasePageSearchCommand;
import linky.dto.PageUserBean;
import linky.infra.Command;

public class FindUsersPaged extends BasePageSearchCommand implements Command<PageUserBean> {

	public FindUsersPaged(Integer page, Integer size, String sortField, String sortDirection, String search) {
		super(page, size, sortField, sortDirection, search);
	}

	@Override
	public String toLogString() {
		return "FindUsersPaged{" +
				"page=" + page +
				", size=" + size +
				", sortField=" + sortField +
				", sortDirection=" + sortDirection +
				", search='" + search + '\'' +
				'}';
	}
}
