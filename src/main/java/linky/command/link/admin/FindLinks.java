package linky.command.link.admin;

import com.google.common.collect.ImmutableList;
import linky.dto.PageLinksBeanSimple;
import linky.infra.Command;

import java.util.Collection;

public class FindLinks implements Command<PageLinksBeanSimple> {

	private int page = 0;
	private int size = 20;
	private final String search;

	public FindLinks(String search) {
		this.search = search;
	}

	public FindLinks(int page, int size, String search) {
		this(search);
		this.page = page;
		this.size = size;
	}

	public Collection<TxFlag> txFlags() {
		return ImmutableList.of(TxFlag.READ_ONLY);
	}

	public int page() {
		return this.page;
	}

	public int size() {
		return this.size;
	}

	public String search() {
		return this.search;
	}

	@Override
	public String toLogString() {
		return "FindLinks{" +
				"page=" + page +
				", size=" + size +
				", search='" + search + '\'' +
				'}';
	}
}
