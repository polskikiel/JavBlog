package pl.blog.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.blog.domain.Article;
import pl.blog.domain.Comment;

import java.util.List;

/**
 * Created by Mike on 01.07.2017.
 */
@Repository
public interface ArticleRepo extends CrudRepository<Article, Long> {
    List<Article> findAllByOrderByVisitCounterDesc();

    List<Article> findAllByOrderByCommentsCounterDesc();

    List<Article> findAllByOrderByDateDesc();

    List<Article> findAllByOrderByDateAsc();

    List<Article> findTop10ByOrderByVisitCounterDesc();

    List<Article> findTop4ByOrderByDateDesc();
}
