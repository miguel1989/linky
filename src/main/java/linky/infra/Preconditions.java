package linky.infra;

import linky.exception.ValidationFailed;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Preconditions {
	private final Collection<Condition> conditions = new LinkedList<>();

	//	@Override
	public <T> Condition whenNot(Supplier<T> value, Predicate<T> predicate) {
		return when(value, predicate.negate());
	}

	//	@Override
	public <T> Condition when(Supplier<T> value, Predicate<T> predicate) {
		Condition<T> condition = new Condition<>(value, predicate);
		conditions.add(condition);
		return condition;
	}

	public void check() {
		conditions.stream()
				.filter(Condition::isTrue)
				.findFirst()
				.ifPresent(condition -> {
					throw new ValidationFailed(condition.message());
				});
	}
}
