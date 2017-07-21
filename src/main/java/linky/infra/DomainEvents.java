package linky.infra;

public interface DomainEvents {
	void publish(DomainEvent domainEvent);

	static DomainEvents ephemeral() {
		return EphemeralDomainEvents.instance();
	}
}
