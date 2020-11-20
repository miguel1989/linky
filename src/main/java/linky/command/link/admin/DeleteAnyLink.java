package linky.command.link.admin;

import linky.infra.Command;

public class DeleteAnyLink implements Command<Command.R.Void> {
	private final String id;

	public DeleteAnyLink(String id) {
		this.id = id;
	}

	public String id() {
		return this.id;
	}

	@Override
	public String toLogString() {
		return "DeleteAnyLink(id = " + id + " )";
	}
}
