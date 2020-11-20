package linky.command.link.admin;

import com.google.common.collect.ImmutableList;
import linky.dto.LinkBean;
import linky.infra.Command;

import java.util.Collection;

public class FindAnyLink implements Command<LinkBean> {
	private final String id;

	public FindAnyLink(String id) {
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
		return "FindAnyLink(id = " + id + " )";
	}
}
