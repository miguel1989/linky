package linky.dto;

import linky.domain.Visit;

import java.time.LocalDateTime;

public class VisitBean {

	public String id;
	public String ip;
	public String country;
	public LocalDateTime createdAt;
	public String data;

	public VisitBean() {

	}

	public VisitBean(Visit visit) {
		this.id = visit.id().toString();
		this.ip = visit.ip();
		this.country = visit.country();
		this.createdAt = visit.createdAt();
		this.data = visit.data();
	}
}
