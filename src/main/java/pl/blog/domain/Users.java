package pl.blog.domain;

import org.hibernate.annotations.CollectionId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Mike on 03.08.2017.
 */
@Entity
@Table(name = "user")
public class Users implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "nick")
    private String nick;
    @Column(name = "mail")
    private String mail;
    @Column(name = "imgUrl")
    private String imgUrl;

    private LocalDate birthDate;
    private String description;

    @Column(name = "active")
    private int active;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns =
            @JoinColumn(name = "user_id"),
            inverseJoinColumns =
            @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "addressee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> recivedMessages = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Article> articles = new ArrayList<>();

    private Integer messageCount = 0;

    public Users() {
    }

    public Users(Users users) {
        this.active = users.getActive();
        this.mail = users.getMail();
        this.birthDate = users.getBirthDate();
        this.roles = users.getRoles();
        this.username = users.getUsername();
        this.password = users.getPassword();

        this.id = users.getId();
    }

    public Users(String name, String mail, LocalDate birthDate, String imgUrl) {       //msg bot
        this.username = name;
        this.mail = mail;
        this.birthDate = birthDate;
        this.active = 0;
        this.imgUrl = imgUrl;
    }

    public Users(String username, String password, String mail, LocalDate birthDate) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.birthDate = birthDate;
        this.active = 1;
    }

    public Users(String username, String password, String mail, String imgUrl, LocalDate localDate, int active, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.imgUrl = imgUrl;
        this.active = active;
        this.birthDate = localDate;
        this.roles = roles;
    }


    public List<Message> getRecivedMessages() {
        return recivedMessages;
    }

    public void setRecivedMessages(List<Message> recivedMessages) {
        this.recivedMessages = recivedMessages;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
