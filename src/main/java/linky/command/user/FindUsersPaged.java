package linky.command.user;

import com.google.common.collect.ImmutableList;
import linky.dto.PageUserBean;
import linky.infra.Command;

import java.util.Collection;

public class FindUsersPaged implements Command<PageUserBean> {

	private final int page;
	private final int size;
	private final String searchStr;

	public FindUsersPaged(int page, int size, String searchStr) {
		this.page = page;
		this.size = size;
		this.searchStr = searchStr;
	}

	public int page() {
		return this.page;
	}

	public int size() {
		return this.size;
	}

	public String searchStr() {
		return searchStr;
	}

	public Collection<TxFlag> txFlags() {
		return ImmutableList.of(TxFlag.READ_ONLY);
	}

	@Override
	public String toLogString() {
		return "FindUsersPaged{" +
				"page=" + page +
				", size=" + size +
				'}';
	}
}
