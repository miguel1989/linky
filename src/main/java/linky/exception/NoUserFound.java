package linky.exception;

public class NoUserFound extends RuntimeException {
	public NoUserFound(String msg) {
		super(msg);
	}
}
