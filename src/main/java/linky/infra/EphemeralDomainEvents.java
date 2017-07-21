package linky.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.empty;

@Component
public class EphemeralDomainEvents implements DomainEvents {

	private static DomainEvents instance;

	private final ExecutorService executor = Executors.newWorkStealingPool();
	private final TransactionTemplate tx;
	private final SideEffects sideEffects;

	@Autowired
	public EphemeralDomainEvents(SideEffects sideEffects, TransactionTemplate tx) {
		EphemeralDomainEvents.instance = this;
		this.sideEffects = sideEffects;
		this.tx = tx;
	}

	@Override
	public void publish(DomainEvent domainEvent) {
		Collection<SideEffect> eventSideEffects = sideEffects.byEvent(domainEvent);

		if (eventSideEffects != null) {
			eventSideEffects.forEach(sideEffect ->
					inFuture(inTx(transactionStatus -> sideEffect.occur(domainEvent)))
			);
		}
	}

	private CompletableFuture<Void> inFuture(Runnable runnable) {
		return CompletableFuture.runAsync(runnable, executor);
	}

	private Runnable inTx(Consumer<TransactionStatus> consumer) {
		return () -> tx.execute(status -> {
			consumer.accept(status);
			return empty();
		});
	}

	public static DomainEvents instance() {
		return checkNotNull(instance, "Ephemeral domain events have not been initialized yet.");
	}
}
