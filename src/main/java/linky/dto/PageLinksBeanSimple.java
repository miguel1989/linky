package linky.dto;

import linky.infra.Command;

public class PageLinksBeanSimple implements Command.R {

	public RestResponsePage<LinkBeanSimple> pageLinks;

	public PageLinksBeanSimple() {

	}

	public PageLinksBeanSimple(RestResponsePage<LinkBeanSimple> pageLinks) {
		this.pageLinks = pageLinks;
	}
}
