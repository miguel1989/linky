package linky.dto;

import linky.infra.Command;
import org.springframework.data.domain.Page;

public class PageLinksBeanSimple implements Command.R {

	public Page<LinkBeanSimple> pageLinks;

	public PageLinksBeanSimple() {

	}

	public PageLinksBeanSimple(Page<LinkBeanSimple> pageLinks) {
		this.pageLinks = pageLinks;
	}
}
