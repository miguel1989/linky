package linky.dto;

public class CreateLinkBean {
	public String name;
	public String url;

	public CreateLinkBean() {

	}

	public CreateLinkBean(String name, String url) {
		this.name = name;
		this.url = url;
	}
}
