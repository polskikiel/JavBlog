package pl.blog.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.blog.domain.Message;

/**
 * Created by Mike on 08.08.2017.
 */
@Repository
public interface MessagesRepo extends CrudRepository<Message, Long> {
}
