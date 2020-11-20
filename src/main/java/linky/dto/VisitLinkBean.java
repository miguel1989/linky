package linky.dto;

import linky.infra.Command;

public class VisitLinkBean implements Command.R {
	public String url;

	public VisitLinkBean() {

	}

	public VisitLinkBean(String url) {
		this.url = url;
	}
}
