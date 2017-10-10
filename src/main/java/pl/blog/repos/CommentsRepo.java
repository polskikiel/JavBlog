package pl.blog.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.blog.domain.Comment;

import java.util.List;

/**
 * Created by Mike on 01.07.2017.
 */
@Repository
public interface CommentsRepo extends CrudRepository<Comment, Long> {
    List<Comment> findByArticleId(Long id);

    List<Comment> findByArticleIdOrderByDateDesc(Long id);
}
