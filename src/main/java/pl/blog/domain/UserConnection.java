package pl.blog.domain;

import pl.blog.domain.io.UserConnectionIO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class UserConnection implements UserConnectionIO, Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Long user_id;
    private boolean enable;
    private Date startSession;
    private Date endSession;
    private Date lastAccess;
    private String ip;

    public UserConnection() {
    }

    public UserConnection(UserConnectionIO userConnectionIO) {
        this.user_id = userConnectionIO.getUser_id();
        this.enable = userConnectionIO.isEnable();
        this.startSession = userConnectionIO.getStartSessionDate();
        this.endSession = userConnectionIO.getEndSessionDate();
        this.ip = userConnectionIO.getIp();
        this.id = userConnectionIO.getId();
        this.lastAccess = userConnectionIO.getLastAccess();
    }

    @Override
    public Long getUser_id() {
        return user_id;
    }

    @Override
    public void setUser_id(Long id) {
        this.user_id = id;
    }

    @Override
    public Date getStartSessionDate() {
        return this.startSession;
    }

    @Override
    public void setStartSessionDate(Date startSessionDate) {
        this.startSession = startSessionDate;
    }

    @Override
    public Date getEndSessionDate() {
        return endSession;
    }

    @Override
    public void setEndSessionDate(Date endSessionDate) {
        this.endSession = endSessionDate;
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String getIp() {
        return this.ip;
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public Date getLastAccess() {
        return lastAccess;
    }

    @Override
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
