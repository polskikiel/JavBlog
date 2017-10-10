package pl.blog.services.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.blog.domain.Article;
import pl.blog.domain.BasicArticle;
import pl.blog.domain.Users;
import pl.blog.dto.ArticleDTO;
import pl.blog.repos.BasicArticleRepo;

import javax.annotation.PostConstruct;

@Component
public class BasicArticlesCreator {
    private Users adminUser;
    private BasicArticleRepo articleRepo;

    @Autowired
    public BasicArticlesCreator(Users adminUser, BasicArticleRepo articleRepo) {
        this.adminUser = adminUser;
        this.articleRepo = articleRepo;
    }

    @PostConstruct
    public void init() {
        BasicArticle basicArticle = new BasicArticle();

        basicArticle.setHeader("Witaj na <p id=\"javalabel\">JAVA</p> Blogu");
        basicArticle.setBody("Jestem początkującym programistą JAVA i w ramach tego blogu postaram się przybliżyć mój sposób nauki programowania w tym języku.\n" +
                "\n" +
                "Pokaże z jakich źródeł korzystam podczas nauki, a także stworzę kilka przykładowych projektów.\n" +
                "\n" +
                "Zapraszam do korzystania :) ");
        basicArticle.setImgUrl("https://www.w3schools.com/css/img_fjords.jpg");
        basicArticle.setCategory(new String[]{"Other"});
        basicArticle.setUser(adminUser);

        articleRepo.save(basicArticle);
    }
}
