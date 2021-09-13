package linky.command.link;

public class UpdateLink extends CreateLink {

	private final String linkId;

	public UpdateLink(String userId, String name, String url, String linkId) {
		super(userId, name, url);
		this.linkId = linkId;
	}

	public String linkId() {
		return this.linkId;
	}

	@Override
	public String toLogString() {
		return "UpdateLink ( userId=" + this.userId + ", name=" + this.name + ", url=" + this.url + ", linkId = " + this.linkId + " )";
	}
}
