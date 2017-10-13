package pl.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import pl.blog.converters.TextToHTML;
import pl.blog.domain.Article;
import pl.blog.domain.Comment;
import pl.blog.domain.CommentComments;
import pl.blog.dto.*;
import pl.blog.dto.profileEdits.TextDTO;
import pl.blog.repos.ArticleRepo;
import pl.blog.services.ArticleServices;
import pl.blog.services.io.UserServices;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by Mike on 15.07.2017.
 */
@Controller
public class ArticleController {

    UserServices userServices;
    ArticleServices services;

    @Autowired
    public ArticleController(UserServices userServices, ArticleServices services) {
        this.userServices = userServices;
        this.services = services;
    }

    @ModelAttribute("cat")
    public String[] category() {
        return services.getLabels();
    }

    @GetMapping("/articles")
    public String getArticles(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response,
                              @RequestParam(value = "sort", required = false, defaultValue = "0") Integer sort,
                              @RequestParam(value = "search", required = false) String search,
                              @RequestParam(value = "tag", required = false) String[] categories,
                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
                              /*@CookieValue(value = "lastSearch", required = false, defaultValue = "") String lastSearch*/) {

        model.addAttribute("sort", new SortDTO());
        model.addAttribute("text", new SearchArticleDTO());
        model.addAttribute("sortOptions", services.getSortOptions());

        if (sort != 0 || search != null || categories != null) {
            model.addAttribute("queryString", request.getQueryString().split("&page")[0]);
        }

    /*    if (!lastSearch.isEmpty())
            model.addAttribute("lastSearch", lastSearch);*/

        session.setAttribute("articles", services.getArticlesWithoutWelcomePage());

        if (sort == 0) {
            session.setAttribute("sortNr", sort);
        }

        switch ((Integer) session.getAttribute("sortNr")) {         // INVIDUAL CONTROLLER FOR THIS AND sortedArticles as SESSION ATT
            case 0: {
                model.addAttribute("sortedArticles",
                        services.searchArticles(services.getArticlesWithoutWelcomePage(), search, categories));
            }
            break;
            case 1: {
                model.addAttribute("sortedArticles",
                        services.searchArticles(services.mostVisited(), search, categories));
            }
            break;
            case 2: {
                model.addAttribute("sortedArticles",
                        services.searchArticles(services.mostCommented(), search, categories));

            }
            break;
            case 3: {
                model.addAttribute("sortedArticles",
                        services.searchArticles(services.newestArticles(), search, categories));
            }
            break;
            case 4: {
                model.addAttribute("sortedArticles",
                        services.searchArticles(services.oldestArticles(), search, categories));
            }
            break;
        }


        return "articles";
    }



    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/articles/search")
    public String searchArticles(Model model, HttpSession session, HttpServletResponse response, SessionStatus status,
                                 @ModelAttribute("sort") SortDTO sortDTO,
                                 @ModelAttribute("text") SearchArticleDTO searchArticleDTO) {
        model.addAttribute("cat", "");


        if (sortDTO.getSort() != null) {
            if (sortDTO.getSort() != 0) {
                session.setAttribute("sortNr", sortDTO.getSort());      // setting this attribute on session scope because
                // i want the sort option to remember its value till next change of this value
            }
        }
        if (session.getAttribute("sortCache") == null) {
            session.setAttribute("sortCache", "&search=&tag=");
        }

        StringBuilder stringBuilder = new StringBuilder(session.getAttribute("sortCache").toString());

        if (searchArticleDTO.getCategory() != null || searchArticleDTO.getText() != null) {
            if (searchArticleDTO.getText() != null && searchArticleDTO.getCategory() != null) {
                stringBuilder.delete(7, stringBuilder.length() - 1);
                stringBuilder.append(searchArticleDTO.getText() + "&tag=" + services.tagsToRequest(searchArticleDTO.getCategory()));

            } else if (searchArticleDTO.getText() != null) {
                stringBuilder.delete(7, stringBuilder.length() - 1);
                stringBuilder.append(searchArticleDTO.getText() + session.getAttribute("sortCache").toString().split("=")[2].split("&")[0]);

            } else if (searchArticleDTO.getCategory() != null) {
                stringBuilder.delete(7, stringBuilder.length() - 1);
                stringBuilder.append(session.getAttribute("sortCache").toString().split("=")[1].split("&")[0] + "&tag=" + services.tagsToRequest(searchArticleDTO.getCategory()));

            }   // can't send some chars by cookie     char(44)
        } else {
            return "redirect:/articles?sort=" + session.getAttribute("sortNr") + session.getAttribute("sortCache");
        }


        if (session.getAttribute("sortCache") == null && stringBuilder.length() == 13) {
            session.setAttribute("sortCache", "&search=&tag=");
        } else {
            session.setAttribute("sortCache", stringBuilder.toString());
        }


        return "redirect:/articles?sort=" + session.getAttribute("sortNr") + session.getAttribute("sortCache");
    }

   /* @PostMapping("/articles")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String postArticles(HttpSession session,
                               @ModelAttribute("sort") SortDTO sortDTO,
                               @ModelAttribute("text") SearchArticleDTO searchArticleDTO) {

        if (sortDTO.getSort() != null) {
            if (sortDTO.getSort() != 0) {
                session.setAttribute("sortNr", sortDTO.getSort());      // setting this attribute on session scope because
                // i want the sort option to remember its value till next change of this value
            }
        }

        if (session.getAttribute("sortCache") == null) {
            session.setAttribute("sortCache", "&search=&tag=");
        }

        StringBuilder stringBuilder = new StringBuilder(session.getAttribute("sortCache").toString());

        if (searchArticleDTO.getCategory() != null || searchArticleDTO.getText() != null) {
            if (searchArticleDTO.getText() != null && searchArticleDTO.getCategory() != null) {
                stringBuilder.delete(7, stringBuilder.length() - 1);
                stringBuilder.append(searchArticleDTO.getText() + "&tag=" + services.tagsToRequest(searchArticleDTO.getCategory()));

            } else if (searchArticleDTO.getText() != null) {
                stringBuilder.delete(7, stringBuilder.length() - 1);
                stringBuilder.append(searchArticleDTO.getText() + session.getAttribute("sortCache").toString().split("=")[2].split("&")[0]);

            } else if (searchArticleDTO.getCategory() != null) {
                stringBuilder.delete(7, stringBuilder.length() - 1);
                stringBuilder.append(session.getAttribute("sortCache").toString().split("=")[1].split("&")[0] + "&tag=" + services.tagsToRequest(searchArticleDTO.getCategory()));

            }   // can't send some chars by cookie     char(44)
        } else {
            return "redirect:/articles?sort=" + session.getAttribute("sortNr") + session.getAttribute("sortCache");
        }


        if (session.getAttribute("sortCache") == null && stringBuilder.length() == 13) {
            session.setAttribute("sortCache", "&search=&tag=");
        } else {
            session.setAttribute("sortCache", stringBuilder.toString());
        }


        return "redirect:/articles?sort=" + session.getAttribute("sortNr") + session.getAttribute("sortCache");
    }*/

    @GetMapping("/article/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getArticleById(Model model, HttpServletRequest request,
                                 @PathVariable("id") Long id,
                                 @RequestParam(value = "edit", required = false) String edit,
                                 @RequestParam(value = "delete", required = false) String delete,
                                 @RequestParam(value = "page", defaultValue = "0") Integer page) {
        model.addAttribute("pageNr", page);
        model.addAttribute("comment", new TextDTO());
        model.addAttribute("commentComment", new CommentCommentsDTO());

        try {
            services.articleVisitCounter(id);
            model.addAttribute("comments", services.getCommentsFromArticleId(id));
            model.addAttribute("article", TextToHTML.articleTexts(services.getArticleById(id)));

            if (isCreator(request, id)) {
                if (delete != null) {
                    services.deleteArticle(id);
                    return "redirect:/profile#yourArticles";
                }

                if (edit != null) {
                    ArticleDTO articleDTO = new ArticleDTO();
                    articleDTO.setBody(services.getArticleById(id).getBody());
                    model.addAttribute("articleToEdit", articleDTO);
                    model.addAttribute("editMode", true);
                    return "article";
                }
            }
        } catch (NullPointerException npe) {
            return "redirect:/articles";
        }

        return "article";
    }

    @PostMapping("/article/{id}")
    public String postCommentByArticleId(Model model, HttpServletRequest request,
                                         @PathVariable("id") Long id,
                                         @ModelAttribute("comment") TextDTO textDTO,
                                         @ModelAttribute("commentComment") CommentCommentsDTO commentCommentsDTO,
                                         @RequestParam(value = "edit", required = false) String edit,
                                         @ModelAttribute("article") ArticleDTO articleDTO) {
        model.addAttribute("commentComment", new CommentCommentsDTO());
        model.addAttribute("comment", new TextDTO());

        if (textDTO.getText() != null) {
            services.addComment(new Comment(textDTO.getText(),
                    userServices.getByName(request.getUserPrincipal().getName()), services.getArticleById(id)));
        } else {
            services.addCommentToComment(new CommentComments(
                            userServices.getByName(request.getUserPrincipal().getName()),

                            services.getCommentById(commentCommentsDTO.getCommentId()),

                            commentCommentsDTO.getBody()),

                    commentCommentsDTO.getCommentId());
        }

        return "redirect:/article/" + id;
    }

    @GetMapping("/newarticle")
    public String getArticle(Model model) {
        model.addAttribute("article", new ArticleDTO());

        return "addarticle";
    }

    @PostMapping("/newarticle")
    public String postArticle(@ModelAttribute("article") ArticleDTO articleDTO,
                              Model model, HttpServletRequest request) {

        Map<String, String> map = services.failures(articleDTO);

        if (map != null) {      // null if empty from errors
            model.addAttribute("faliure", map);
            return "/addarticle";
        }
        Article article = new Article(articleDTO);
        if (article.getImgUrl().isEmpty()) {
            article.setImgUrl("http://prg.is.titech.ac.jp/wp-content/uploads/2013/09/prg-banner-201309.jpg");
        }
        services.saveOrUpdate(article, request.getUserPrincipal().getName());

        return "redirect:/";
    }

    private boolean isCreator(HttpServletRequest request, Long id) {
        return services.getArticleById(id).getUser().getUsername().equals(request.getUserPrincipal().getName());
    }

}
