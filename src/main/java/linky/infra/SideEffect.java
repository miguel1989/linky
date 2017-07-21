package linky.infra;

import com.google.common.reflect.TypeToken;

public interface SideEffect<T extends DomainEvent> {

	void occur(T event);

	default TypeToken<T> eventType() {
		return new TypeToken<T>(getClass()) {
		};
	}
}
