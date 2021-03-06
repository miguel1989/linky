package linky.command.user;

import linky.infra.Command;

public class DeleteUser implements Command<Command.R.Void> {

	private final String email;

	public DeleteUser(String email) {
		this.email = email;
	}

	public String email() {
		return this.email;
	}

	@Override
	public String toLogString() {
		return "DeleteUser(email=" + email + " )";
	}
}
