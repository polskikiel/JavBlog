package pl.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.blog.domain.Article;
import pl.blog.repos.ArticleRepo;
import pl.blog.services.ArticleServices;
import pl.blog.services.UserConnectionServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Mike on 16.07.2017.
 */
@Controller
public class LoginController {
    @Autowired
    UserConnectionServices connectionServices;

    @Autowired
    ArticleServices articleServices;

    @GetMapping("/login**")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        List<Article> articles = articleServices.xRandomArticles(4);

        model.addAttribute("sidebarArticles", articles);
        model.addAttribute("body", articleServices.articlesFirstLetters(articles));

        if (error != null)
            model.addAttribute("error", "Given login or password was wrong");
        if (logout != null) {
            model.addAttribute("error", "You have been logged out!");
            try {
                connectionServices.endConnection();
            } catch (NullPointerException npe) {
                //System.out.println("No session");
            }
        }

        return "login";
    }

    @RequestMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();     // IT GIVES INFO ABOUT AUTH
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);

        }
        return "redirect:/login?logout";
    }


}
