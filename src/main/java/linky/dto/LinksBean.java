package linky.dto;

import linky.infra.Command;

import java.util.List;

public class LinksBean implements Command.R {
	public List<LinkBean> links;
	
	public LinksBean() {
		
	}
	
	public LinksBean(List<LinkBean> links) {
		this.links = links;
	}
}
