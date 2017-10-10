package pl.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.blog.domain.UserConnection;
import pl.blog.repos.UserConnectionRepo;
import pl.blog.services.components.ConnectionSession;
import pl.blog.services.io.UserServices;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserConnectionServices {
    private UserConnectionRepo connectionRepo;
    private ConnectionSession connectionSession;
    private UserServices userServices;
    private Date requestDate;
    private Date sessionDate;
    private static final long MAX_INACTIVE_SESSION_TIME = 3 * 100000; // time of the session 300s


    @Autowired
    public UserConnectionServices(UserConnectionRepo connectionRepo, ConnectionSession connectionSession, UserServices userServices, Date requestDate, Date sessionDate) {
        this.connectionRepo = connectionRepo;
        this.connectionSession = connectionSession;
        this.userServices = userServices;
        this.requestDate = requestDate;
        this.sessionDate = sessionDate;
    }

    private UserConnection userConnection;

    public void startConnection() {
        userConnection = new UserConnection(connectionSession);
        userConnection.setStartSessionDate(sessionDate);
        connectionRepo.save(userConnection);
    }

    public void lastAccess() {
        userConnection.setLastAccess(requestDate);
        connectionRepo.save(userConnection);
    }

    public void endConnection() {
        userConnection.setEndSessionDate(requestDate);
        userConnection.setEnable(false);

        connectionRepo.save(userConnection);
    }

    public void setUser(Long id) {
        userConnection.setUser_id(id);
    }

    public List<UserConnection> getAll() {
        return (List<UserConnection>) connectionRepo.findAll();
    }

    public List<UserConnection> connectionsToClear() {
        List<UserConnection> list = getAll();
        List<UserConnection> connectionList = new ArrayList<>();

        for (UserConnection connection : list) {
            if (connection.getLastAccess().getTime() - connection.getStartSessionDate().getTime() > MAX_INACTIVE_SESSION_TIME
                    || !connection.isEnable()) {

                connectionList.add(connection);
            }
        }
        return connectionList;
    }

    public void deleteList(List<UserConnection> userConnections) {
        connectionRepo.delete(userConnections);
    }

    public UserConnection getUserConnection() {
        return userConnection;
    }

    public void setUserConnection(UserConnection userConnection) {
        this.userConnection = userConnection;
    }

    public String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

    public ConnectionSession getConnectionSession() {
        return connectionSession;
    }

    public void setConnectionSession(ConnectionSession connectionSession) {
        this.connectionSession = connectionSession;
    }
}
