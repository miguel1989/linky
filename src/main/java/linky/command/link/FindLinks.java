package linky.command.link;

import com.google.common.collect.ImmutableList;
import linky.dto.PageLinksBeanSimple;
import linky.infra.Command;

import java.util.Collection;

public class FindLinks implements Command<PageLinksBeanSimple> {

	private int page = 0;
	private int size = 20;
	private String name;
	private String url;

	public FindLinks(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public FindLinks(int page, int size, String name, String url) {
		this(name, url);
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

	public String name() {
		return this.name;
	}

	public String url() {
		return this.url;
	}

	@Override
	public String toLogString() {
		return "FindLinks{" +
				"page=" + page +
				", size=" + size +
				", name='" + name + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
