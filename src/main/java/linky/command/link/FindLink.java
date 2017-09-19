package linky.command.link;

import com.google.common.collect.ImmutableList;
import linky.dto.LinkBean;
import linky.infra.Command;

import java.util.Collection;

public class FindLink implements Command<LinkBean> {

	//todo maybe allow also to find link by name
	private String id;

	public FindLink(String id) {
		this.id = id;
	}

	public Collection<TxFlag> txFlags() {
		return ImmutableList.of(TxFlag.READ_ONLY);
	}

	public String id() {
		return this.id;
	}

	@Override
	public String toLogString() {
		return "FindLink(id = " + id + " )";
	}
}
