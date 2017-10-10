package pl.blog.domain;

import org.hibernate.annotations.Type;
import pl.blog.domain.io.ArticleIO;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class BasicArticle implements Serializable, ArticleIO {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    private String header;
    private String[] tags;
    private String[] category;

    private String imgUrl;

    @Type(type = "string")
    @Size(max = 100000)
    private String body;

    public BasicArticle() {
        date = Calendar.getInstance().getTime();
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private Date date;
    private Integer visitCounter = 0;
    private Integer commentsCounter = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getVisitCounter() {
        return visitCounter;
    }

    public void setVisitCounter(Integer visitCounter) {
        this.visitCounter = visitCounter;
    }

    public Integer getCommentsCounter() {
        return commentsCounter;
    }

    public void setCommentsCounter(Integer commentsCounter) {
        this.commentsCounter = commentsCounter;
    }
}
