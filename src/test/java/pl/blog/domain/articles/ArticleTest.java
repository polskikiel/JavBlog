package pl.blog.domain.articles;

import org.junit.Assert;
import org.junit.Test;
import pl.blog.domain.Article;

/**
 * Created by Mike on 01.07.2017.
 */
public class ArticleTest {
    @Test
    public void setTags() throws Exception {
        Article article = new Article();
        article.setHeader("Dolaczam sie do prosby pana Elegenxczxlc w sprawie sprawy");
        for(String a : article.getTags()){
            Assert.assertTrue(article.getHeader().contains(a));
        }
    }


}