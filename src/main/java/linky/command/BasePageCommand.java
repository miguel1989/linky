package linky.command;

import com.google.common.collect.ImmutableList;
import linky.infra.Command;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.Collection;

public abstract class BasePageCommand {

	protected final Integer page;
	protected final Integer size;
	protected final String sortField;
	protected Sort.Direction sortDirection = Sort.Direction.DESC;

	public BasePageCommand(Integer page, Integer size, String sortField, String sortDirection) {
		if (page == null) {
			page = 0;
		}
		if (size == null) {
			size = 20;
		}
		if (StringUtils.isBlank(sortField)) {
			sortField = "id";
		}
		if (StringUtils.isNotBlank(sortDirection)) {
			this.sortDirection = Sort.Direction.valueOf(sortDirection.toUpperCase());
		}
		this.page = page;
		this.size = size;
		this.sortField = sortField;
	}

	public Collection<Command.TxFlag> txFlags() {
		return ImmutableList.of(Command.TxFlag.READ_ONLY);
	}

	public int page() {
		return this.page;
	}

	public int size() {
		return this.size;
	}

	public String getSortField() {
		return sortField;
	}

	public Sort.Direction getSortDirection() {
		return sortDirection;
	}
}
