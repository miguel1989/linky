package linky.command.link;

import com.google.common.collect.ImmutableList;
import linky.dto.LinksBean;
import linky.dto.PageLinksBeanSimple;
import linky.infra.Command;

import java.util.Collection;

public class FindLinksForUser implements Command<PageLinksBeanSimple> {

	private final String userId;
	private int page = 0;
	private int size = 20;

	public FindLinksForUser(String userId, int page, int size) {
		this.userId = userId;
		this.page = page;
		this.size = size;
	}

	public String userId() {
		return userId;
	}

	public int page() {
		return this.page;
	}

	public int size() {
		return this.size;
	}

	public Collection<TxFlag> txFlags() {
		return ImmutableList.of(TxFlag.READ_ONLY);
	}

	@Override
	public String toLogString() {
		return "FindLinksForUser(userId = " + userId + " )";
	}
}
