package linky.command.link;

import com.google.common.collect.ImmutableList;
import linky.dto.LinksBean;
import linky.infra.Command;

import java.util.Collection;

public class FindLinksForUser implements Command<LinksBean> {

	private String userId;

	public FindLinksForUser(String userId) {
		this.userId = userId;
	}
	
	public String userId() {
		return userId;
	}

	public Collection<TxFlag> txFlags() {
		return ImmutableList.of(TxFlag.READ_ONLY);
	}

	@Override
	public String toLogString() {
		return "FindLinksForUser(userId = " + userId + " )";
	}
}
