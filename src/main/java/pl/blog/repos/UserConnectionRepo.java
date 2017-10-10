package pl.blog.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pl.blog.domain.UserConnection;

@Repository
public interface UserConnectionRepo extends CrudRepository<UserConnection, Long> {
}
