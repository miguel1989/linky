package linky.dto;

import linky.domain.Link;
import linky.infra.Command;

import java.util.Collection;
import java.util.LinkedList;

public class LinkBean implements Command.R {
	public String id;
	public String name;
	public String url;
	public Collection<VisitBean> visits;
	
	public LinkBean() {
		
	}
	
	public LinkBean(Link link) {
		this.id = link.id().toString();
		this.name = link.name();
		this.url = link.url();
		this.visits = new LinkedList<>();
		link.visits().forEach(visit -> visits.add(new VisitBean(visit)));
	}
}
