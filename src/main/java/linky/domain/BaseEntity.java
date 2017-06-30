package linky.domain;

import com.datastax.driver.core.utils.UUIDs;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalTime;
import java.util.UUID;

public abstract class BaseEntity {
	
	@Id
	@Column(name = "id")
	private UUID id = UUIDs.timeBased();

	@Column(name = "created_at")
	private LocalTime createdAt = LocalTime.now();

	public UUID id() {
		return id;
	}
	
	public LocalTime createdAt() {
		return createdAt;
	}
}
