package linky.dao;

import linky.domain.Link;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkDao extends PagingAndSortingRepository<Link, UUID>, JpaSpecificationExecutor<Link> {
	List<Link> findByCreatedBy(String createdBy);
	Optional<Link> findByName(String name);
//	List<Link> findByNameLikeOrUrlLike(String name, String url);
}
