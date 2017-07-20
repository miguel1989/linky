package linky.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "link_visits")
public class Visit extends BaseEntity {

//	@Embedded
//	private LinkId linkId;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "link_id")
	private Link link;

	@Column(name = "ip", length = 20)
	private String ip;

	@Column(name = "country", length = 120)
	private String country = "pending";//static const

	@Column(name = "json_data")
	@Basic(fetch = FetchType.LAZY)
	@Lob
	@Type(type = "org.hibernate.type.TextType")
	private String data;//full json data from provider

	//default constructor for hibernate
	public Visit() {

	}

	public Visit(Link link, String ip) {
		this.link = link;
		this.ip = ip;
	}

	public void tag(String country, String data) {
		this.country = country;
		this.data = data;
	}

	public Link link() {
		return this.link;
	}

	public String ip() {
		return this.ip;
	}

	public String country() {
		return this.country;
	}

	public String data() {
		return data;
	}
}
