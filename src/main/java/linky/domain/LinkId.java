package linky.domain;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class LinkId {
	private UUID id;
	
	public void id(UUID id) {
		this.id = id;
	}
	public UUID id() {
		return id;
	}
}
