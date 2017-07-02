package linky.exceptions;

import java.lang.reflect.Type;

public class NoReactionFound extends RuntimeException {
	public NoReactionFound(Type type) {
		super("No reaction found for type " + type);
	}
}
