package pl.blog.services.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import pl.blog.domain.io.UserConnectionIO;

import javax.annotation.PostConstruct;
import javax.persistence.Transient;
import java.util.Calendar;
import java.util.Date;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
// proxy makes Session scope object to be usable in Singleton scope services

public class ConnectionSession implements UserConnectionIO {

    private Long id;

    private Long user_id;

    @Value("true")
    private boolean enable;

    private String ip;

    private Date startSessionDate;

    private Date lastAccess;

    private Date endSessionDate;

/*    @PostConstruct
    public void init() {    }*/


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
    public String getIp() {
        return ip;
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public Date getStartSessionDate() {
        return startSessionDate;
    }

    @Override
    public void setStartSessionDate(Date startSessionDate) {
        this.startSessionDate = startSessionDate;
    }

    @Override
    public Date getEndSessionDate() {
        return endSessionDate;
    }

    @Override
    public void setEndSessionDate(Date endSessionDate) {
        this.endSessionDate = endSessionDate;
    }


    public ConnectionSession() {
    }

    @Override
    public Long getUser_id() {
        return user_id;
    }

    @Override
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
