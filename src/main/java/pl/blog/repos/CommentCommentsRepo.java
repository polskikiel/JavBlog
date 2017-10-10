package pl.blog.repos;

import org.springframework.data.repository.CrudRepository;
import pl.blog.domain.CommentComments;

public interface CommentCommentsRepo extends CrudRepository<CommentComments, Long> {
}
