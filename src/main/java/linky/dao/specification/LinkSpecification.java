package linky.dao.specification;

import com.google.common.base.Strings;
import linky.domain.Link;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;

public class LinkSpecification {

	public static Specification<Link> byNameOrUrl(String name, String url) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			final Collection<Predicate> predicates = new ArrayList<>();

			if (!Strings.isNullOrEmpty(name)) {
				final Predicate namePredicate = criteriaBuilder.like(root.get("name"), name);
				predicates.add(namePredicate);
			}
			if (!Strings.isNullOrEmpty(url)) {
				final Predicate urlPredicate = criteriaBuilder.like(root.get("url"), url);
				predicates.add(urlPredicate);
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}
}
