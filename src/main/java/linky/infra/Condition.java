package linky.infra;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class Condition<T> {
	private final Supplier<T> item;
	private final Predicate<T> predicate;
	private String failureMessage;
	public Condition(Supplier<T> item, Predicate<T> predicate) {
		this.item = item;
		this.predicate = predicate;
	}
	public void then(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	boolean isTrue() {
		return predicate.test(item.get());
	}
	String message() {
		return failureMessage.replaceAll("%s", item.toString());
	}
}
