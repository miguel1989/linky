package linky.dao.specification;

import com.google.common.collect.Lists;
import linky.domain.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.List;

public class UserSearchSpecification {
	private static final String SQL_PERCENT = "%";

	private final String searchStr;

	//todo maybe rework with searchable field on the USER
	private final List<String> fields = Lists.newArrayList("name", "email");

	public UserSearchSpecification(String searchStr) {
		this.searchStr = SQL_PERCENT + searchStr.toUpperCase() + SQL_PERCENT;
	}

	public Specification<User> build() {
		return (root, criteriaQuery, cb) -> cb.or(
				fields.stream()
						.map(field -> cb.like(cb.upper(root.get(field)), searchStr))
						.toArray(Predicate[]::new)
		);
	}
}
