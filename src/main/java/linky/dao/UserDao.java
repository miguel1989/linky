package linky.dao;

import linky.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.UUID;

public interface UserDao extends PagingAndSortingRepository<User, UUID> {
}
