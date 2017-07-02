package linky.domain;

import com.google.common.collect.ImmutableList;
import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

@Entity
@Table(name = "links")
public class Link extends BaseEntity {

	@Column(name = "name", unique = true)
	private String name;//unique and dictionary with abuse words
	
	@Column(name = "url")
	private String url;//valid url
	
	@Column(name = "created_by")
	private String createdBy;

	@OneToMany(mappedBy = "link", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Visit> visits = new LinkedList<>();

	//default constructor for hibernate
	public Link() {
		
	}
	public Link(String name, String url, String createdBy) {
		this.name = name;
		this.url = url;
		this.createdBy = createdBy;
	}
	
	public Collection<Visit> visits() {
		return ImmutableList.copyOf(visits);
	}
	
	public Visit newVisit(String ip) {
		Visit visit = new Visit(this, ip);
		this.visits.add(visit);
		return visit;
	}
	
	public String name() {
		return name;
	}
	
	public String url() {
		return url;
	}

	public String createdBy() {
		return createdBy;
	}
}