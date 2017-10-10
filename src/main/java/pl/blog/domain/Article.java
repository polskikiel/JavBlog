package pl.blog.domain;

import org.hibernate.annotations.Type;
import pl.blog.domain.io.ArticleIO;
import pl.blog.dto.ArticleDTO;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Mike on 01.07.2017.
 */
@Entity
public class Article implements Serializable, ArticleIO {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    private String header;
    private String[] tags;
    private String[] category;

    private String imgUrl;
    private String dateToString;


    @Type(type = "string")
    @Size(max = 1000000)
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private Date date;
    private Integer visitCounter = 0;
    private Integer commentsCounter = 0;


    public Article(ArticleDTO articleDTO){
        this.body = articleDTO.getBody();
        this.category = articleDTO.getCategory();
        this.imgUrl = articleDTO.getImgUrl();
        setHeader(articleDTO.getTitle());
        date = Calendar.getInstance().getTime();
    }

    public Article() {
        date = Calendar.getInstance().getTime();
    }

    public Integer getVisitCounter() {
        return visitCounter;
    }

    public void setVisitCounter(Integer visitCounter) {
        this.visitCounter = visitCounter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateToString() {
        return dateToString;
    }

    public void setDateToString(String dateToString) {
        this.dateToString = dateToString;
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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
        setTags();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags() {
        this.tags = header.split(" ");
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getCommentsCounter() {
        return commentsCounter;
    }

    public void setCommentsCounter(Integer commentsCounter) {
        this.commentsCounter = commentsCounter;
    }
}

