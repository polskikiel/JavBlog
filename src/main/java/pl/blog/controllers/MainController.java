package pl.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.blog.services.ArticleServices;
import pl.blog.services.MessageServicesImpl;
import pl.blog.services.io.UserServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Mike on 01.07.2017.
 */
@Controller
public class MainController {

    ArticleServices articleServices;
    Facebook facebook;
    ConnectionRepository connectionRepository;

    @Autowired
    public MainController(ArticleServices articleServices, Facebook facebook, ConnectionRepository connectionRepository) {
        this.articleServices = articleServices;
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping("/")
    public String mainPage(Model model, HttpServletRequest request) {
        /*if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {
            System.out.println(facebook.userOperations().getUserProfile().getEmail());
        }*/



        model.addAttribute("sortedArticles", articleServices.getTop10SortedByVisits());
        model.addAttribute("article", articleServices.getWelcomeArticle());
        model.addAttribute("newestArticles", articleServices.top4newestArticles());

        //System.out.println(facebook.getApplicationNamespace());

        return "main";
    }


    @RequestMapping("/noaccess")
    public String noaccess() {
        return "403";
    }

    @RequestMapping("/errors")
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest,
                                        @RequestParam("nr") Integer nr) {

        ModelAndView errorPage = new ModelAndView("403");
        String errorMsg;

        switch (nr) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
            default:{
                errorMsg = "Ups! Something is not ok";
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    @RequestMapping("favicon.ico")
    public static String favicon() {
        return "forward:../favicon.ico";
    }
}
