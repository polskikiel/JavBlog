package pl.blog.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pl.blog.services.UserConnectionServices;
import pl.blog.services.UserServicesImpl;
import pl.blog.services.components.ConnectionSession;
import pl.blog.services.io.UserServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Component
@Transactional
public class ConnectionManagement implements HandlerInterceptor {
    UserConnectionServices userConnectionServices;
    UserServicesImpl userServices;

    private static final long MAX_INACTIVE_SESSION_TIME = 3 * 100000; // time of the session 300s

    @Autowired
    public ConnectionManagement(UserConnectionServices userConnectionServices, UserServicesImpl userServices) {
        this.userConnectionServices = userConnectionServices;
        this.userServices = userServices;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {   // makes session
        if (httpServletRequest.isUserInRole("ROLE_USER")) {
            if (System.currentTimeMillis() - httpServletRequest.getSession().getLastAccessedTime()
                    > MAX_INACTIVE_SESSION_TIME) {
                httpServletResponse.sendRedirect("/logout");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (httpServletRequest.getSession(true).isNew()) {
            ConnectionSession connectionSession = userConnectionServices.getConnectionSession();
            connectionSession.setIp(userConnectionServices.getClientIp(httpServletRequest));
            userConnectionServices.startConnection();
        }
        if (httpServletRequest.isUserInRole("ROLE_USER")) {
            try {
                userConnectionServices.setUser(userServices.getIdFromUsername(httpServletRequest.getUserPrincipal().getName()));
            } catch (NullPointerException npe) {
                return;
            }

        }
        try {
            userConnectionServices.lastAccess();
        } catch (NullPointerException npe) {
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
