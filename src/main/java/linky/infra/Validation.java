package linky.infra;

import com.google.common.reflect.TypeToken;

public interface Validation<C extends Command> {
	void validate(C command);

	default TypeToken<C> commandType() {
		return new TypeToken<C>(getClass()) {
		};
	}
}
