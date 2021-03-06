package linky.command.link;

import linky.dto.VisitLinkBean;
import linky.infra.Command;

public class VisitLink implements Command<VisitLinkBean> {
	public static final String NOT_FOUND = "not_found";

	private final String name;
	private final String ip;

	public VisitLink(String name, String ip) {
		this.name = name;
		this.ip = ip;
	}

	public String name() {
		return name;
	}

	public String ip() {
		return ip;
	}

	@Override
	public String toLogString() {
		return "VisitLink ( name=" + name + ", ip=" + ip + " )";
	}
}
