package linky.exception;

public class ValidationFailed extends RuntimeException {
	public ValidationFailed(String msg) {
		super(msg);
	}
}
