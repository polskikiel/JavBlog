package pl.blog.domain;

import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mike on 08.08.2017.
 */
@Entity
public class Message implements Comparable<Message>, Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Users author;

    @ManyToOne
    @JoinColumn(name = "addressee_id")
    private Users addressee;


    private boolean messageReceived;
    private boolean messageOpened;

    private Date date;

    private String title;
    private String body;


    @Override
    public int compareTo(Message o) {
        return getDate().compareTo(o.getDate());
    }

    public Message() {
    }

    public Message(String body, String title) {
        this.body = body;
        this.title = title;
    }

    public Message(Users author, Users addressee, String body, String title) {
        this.author = author;
        this.addressee = addressee;
        this.body = body;
        this.messageOpened = false;
        this.messageReceived = true;
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isMessageOpened() {
        return messageOpened;
    }

    public Users getAddressee() {
        return addressee;
    }

    public void setAddressee(Users addressee) {
        this.addressee = addressee;
    }

    public void setMessageOpened(boolean messageOpened) {
        this.messageOpened = messageOpened;
    }

    public Users getAuthor() {
        return author;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }

    public boolean isMessageReceived() {
        return messageReceived;
    }

    public void setMessageReceived(boolean messageReceived) {
        this.messageReceived = messageReceived;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
