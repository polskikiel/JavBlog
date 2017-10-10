package pl.blog.repos;

import org.springframework.data.repository.CrudRepository;
import pl.blog.domain.BasicArticle;

public interface BasicArticleRepo extends CrudRepository<BasicArticle, Long> {
}
