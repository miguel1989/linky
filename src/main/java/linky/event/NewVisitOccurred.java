package linky.event;

import linky.infra.DomainEvent;

import java.util.UUID;

public class NewVisitOccurred implements DomainEvent {

	private UUID visitId;
	private String ip;

	public NewVisitOccurred(UUID visitId, String ip) {
		this.visitId = visitId;
		this.ip = ip;
	}

	public UUID visitId() {
		return visitId;
	}

	public String ip() {
		return ip;
	}
}
