package linky.dao;

import linky.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserDao extends PagingAndSortingRepository<User, UUID> {
}
