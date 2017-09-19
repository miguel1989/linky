package linky.dto;

import linky.domain.Link;
import linky.infra.Command;

public class LinkBeanSimple implements Command.R {
	public String id;
	public String name;
	public String url;
	public long visitCount = 0;

	public LinkBeanSimple() {

	}

	public LinkBeanSimple(Link link) {
		this.id = link.id().toString();
		this.name = link.name();
		this.url = link.url();
		this.visitCount = link.visits().stream().count();
	}
}
