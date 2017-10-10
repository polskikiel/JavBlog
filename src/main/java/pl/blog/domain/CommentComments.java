package pl.blog.domain;

import pl.blog.dto.CommentCommentsDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
public class CommentComments implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private String body;

    private Date date;

    public CommentComments(Users user, Comment comment, String body) {
        this.user = user;
        this.comment = comment;
        this.body = body;
        this.date = Calendar.getInstance().getTime();
    }

    public CommentComments() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
