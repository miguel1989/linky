package linky.command.link;

import linky.infra.Command;

public class DeleteMyLink implements Command<Command.R.Void> {
	private final String id;
	private final String userId;

	public DeleteMyLink(String id, String userId) {
		this.id = id;
		this.userId = userId;
	}

	public String id() {
		return this.id;
	}

	public String requestUserId() {
		return this.userId;
	}

	@Override
	public String toLogString() {
		return "DeleteMyLink(id = " + id + " )";
	}
}
