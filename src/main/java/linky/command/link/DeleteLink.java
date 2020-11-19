package linky.command.link;

import linky.infra.Command;

public class DeleteLink implements Command<Command.R.Void> {
	private final String id;

	public DeleteLink(String id) {
		this.id = id;
	}

	public String id() {
		return this.id;
	}

	@Override
	public String toLogString() {
		return "DeleteLink(id = " + id + " )";
	}
}
