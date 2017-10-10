package pl.blog.domain.io;

import java.util.Date;

public interface UserConnectionIO {
    Long getUser_id();

    void setUser_id(Long id);

    Date getStartSessionDate();

    void setStartSessionDate(Date startSessionDate);

    Date getEndSessionDate();

    void setEndSessionDate(Date endSessionDate);

    boolean isEnable();

    void setEnable(boolean enable);

    String getIp();

    void setIp(String ip);

    Date getLastAccess();

    void setLastAccess(Date lastAccess);

    Long getId();

    void setId(Long id);
}
