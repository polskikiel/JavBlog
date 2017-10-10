package pl.blog.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pl.blog.services.ArticleServices;
import pl.blog.services.MessageServicesImpl;
import pl.blog.services.SessionServices;
import pl.blog.services.io.UserServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Mike on 22.08.2017.
 */
@Component
public class SessionManagement implements HandlerInterceptor {
    ArticleServices services;
    UserServices userServices;
    MessageServicesImpl messageServices;
    SessionServices sessionServices;


    private static String lastVisitedSite = "";

    @Autowired
    public SessionManagement(ArticleServices services, UserServices userServices,
                             MessageServicesImpl messageServices, SessionServices sessionServices) {
        this.services = services;
        this.userServices = userServices;
        this.messageServices = messageServices;
        this.sessionServices = sessionServices;
    }

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if ((Integer) request
                .getAttribute("javax.servlet.error.status_code") != null) {

            response.sendRedirect("/errors?nr=" + (Integer) request
                    .getAttribute("javax.servlet.error.status_code"));
        }

        if (session.isNew() || sessionServices.setRefresh(lastVisitedSite)) {       //working

            session.setAttribute("sidebarArticles", services.xRandomArticles(9));   // sidebar articles
            session.setAttribute("articles", services.getArticlesWithoutWelcomePage());
            session.setAttribute("body", services.articlesFirstLetters(services.getAll()));


            if (request.getUserPrincipal() != null) {
                String name = request.getUserPrincipal().getName();

                try {
                    session.setAttribute("mainUser", userServices.getByName(name));
                    session.setAttribute("newMsg", messageServices.countNewMessages(name));
                    session.setAttribute("articleCount", services.getNumberOfUserArticles(name));
                } catch (NullPointerException npe) {
                }

            }
        }
        lastVisitedSite = request.getRequestURI();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
