package linky.command.link;

import com.google.common.collect.ImmutableList;
import linky.dto.LinkBean;
import linky.infra.Command;

import java.util.Collection;

public class FindMyLink implements Command<LinkBean> {

	//todo maybe allow also to find link by name
	private final String id;
	private final String userId;

	public FindMyLink(String id, String userId) {
		this.id = id;
		this.userId = userId;
	}

	public Collection<TxFlag> txFlags() {
		return ImmutableList.of(TxFlag.READ_ONLY);
	}

	public String id() {
		return this.id;
	}

	public String requestUserId() {
		return this.userId;
	}

	@Override
	public String toLogString() {
		return "FindMyLink(id = " + id + " )";
	}
}
