package linky.command.link.admin;

import com.google.common.collect.ImmutableList;
import linky.dto.PageLinksBeanSimple;
import linky.infra.Command;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.Collection;

public class FindLinks implements Command<PageLinksBeanSimple> {

	private final Integer page;
	private final Integer size;
	private final String sortField;
	private Sort.Direction sortDirection = Sort.Direction.DESC;
	private final String search;

	public FindLinks(Integer page, Integer size, String sortField, String sortDirection, String search) {
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
		this.search = search;
	}

	public Collection<TxFlag> txFlags() {
		return ImmutableList.of(TxFlag.READ_ONLY);
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

	public String search() {
		return this.search;
	}

	@Override
	public String toLogString() {
		return "FindLinks{" +
				"page=" + page +
				", size=" + size +
				", sortField=" + sortField +
				", sortDirection=" + sortDirection +
				", search='" + search + '\'' +
				'}';
	}
}
