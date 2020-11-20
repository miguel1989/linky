package linky.dao;

import linky.domain.Visit;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface VisitDao extends PagingAndSortingRepository<Visit, UUID> {
}
