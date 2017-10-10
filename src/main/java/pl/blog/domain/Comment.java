package pl.blog.domain;


import pl.blog.domain.Article;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Mike on 01.07.2017.
 */
@Entity
public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private Date date;

    @Size(max = 200)
    private String body;

    @OneToOne
    private Users user;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentComments> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public Long getId() {
        return id;
    }

    public Comment(String body, Users user, Article article) {
        this.body = body;
        this.user = user;
        this.article = article;
        date = Calendar.getInstance().getTime();
    }

    public Comment() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CommentComments> getComments() {
        return comments;
    }

    public void setComments(List<CommentComments> comments) {
        this.comments = comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
