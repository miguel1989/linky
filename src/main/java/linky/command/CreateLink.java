package linky.command;

import linky.infra.Command;

public class CreateLink implements Command<Command.R.Void> {

	private String userId;
	private String name;
	private String url;

	public CreateLink(String userId, String name, String url) {
		this.userId = userId;
		this.name = name;
		this.url = url;
	}
	
	public String userId() {
		return userId;
	}
	public String name() {
		return name;
	}
	public String url() {
		return url;
	}
}
