package pl.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.blog.domain.Users;
import pl.blog.dto.profileEdits.PassDTO;
import pl.blog.dto.profileEdits.TextDTO;
import pl.blog.repos.UserRepo;
import pl.blog.services.ProfileServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Created by Mike on 04.08.2017.
 */
@Controller
public class ProfileController {
    private ProfileServices profileServices;

    @Autowired
    public ProfileController(ProfileServices profileServices) {
        this.profileServices = profileServices;
    }

    private String[] phlabels =
            {"url", "new nick", "", "new mail", "new description", "", "Your password"};

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/profile")
    public String profile(HttpServletRequest http, Model model,
                          @RequestParam(value = "id", required = false) Long id,
                          @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        if (id == null) {
            Users users = profileServices.getUserServices().getByName(http.getUserPrincipal().getName());     // Optional<> - a container object which may or may not contain a non-null value
            model.addAttribute("profile", users);
            model.addAttribute("userArticle", profileServices.getArticleServices().getArticlesFromUser(http.getUserPrincipal().getName()));

        } else {
            model.addAttribute("userArticle", profileServices.getArticleServices().articlesFromUserId(id));
            Users users = profileServices.getUserServices().getById(id);
            if (users.getUsername().equals(http.getUserPrincipal().getName()))
                return "redirect:/profile";

            model.addAttribute("someprofile", users);
        }

        return "profile";
    }

    @GetMapping("/profile/edit/{edit}")
    public String edit(@PathVariable String edit, Model model, HttpServletRequest http) {
        if (!edit.isEmpty()) {
            if (edit.equals("5")) {
                return "redirect:/messages";
            }
            model.addAttribute("context", edit);        // edit represents url variable value
            model.addAttribute("phlabel", phlabels);
            model.addAttribute("text", new TextDTO());
            model.addAttribute("pass", new PassDTO());
            model.addAttribute("title", ProfileServices.getAttribute(edit));
        }
        return "profile";
    }

    @RequestMapping(value = "/profile/edit/{edit}", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String postEdit(@PathVariable(value = "edit", required = false) String edit, Model model, HttpServletRequest http,
                           @ModelAttribute(value = "text") TextDTO text,
                           @ModelAttribute("pass") PassDTO passDTO) {

        profileServices.setPassDTO(passDTO);
        profileServices.setTextDTO(text);
        Users users = profileServices.getUserServices().getByName(http.getUserPrincipal().getName());

        String faliure = profileServices.faliure(edit, users.getPassword());

        model.addAttribute("context", edit);        // edit represents url variable value
        model.addAttribute("phlabel", phlabels);
        model.addAttribute("text", new TextDTO());
        model.addAttribute("pass", new PassDTO());
        model.addAttribute("title", ProfileServices.getAttribute(edit));

        if (faliure.isEmpty()) {
            switch (edit) {
                case "0": {
                    users.setImgUrl(text.getText());
                    profileServices.getUserServices().saveOrUpdate(users);
                }
                break;
                case "1": {
                    users.setNick(text.getText());
                    profileServices.getUserServices().saveOrUpdate(users);
                }
                break;
                case "2": {
                    users.setPassword(BCrypt.hashpw(passDTO.getPass1(), BCrypt.gensalt(11)));
                    profileServices.getUserServices().saveOrUpdate(users);
                }
                break;
                case "3": {
                    users.setMail(text.getText());
                    profileServices.getUserServices().saveOrUpdate(users);
                }
                break;
                case "4": {
                    users.setDescription(text.getText());
                    profileServices.getUserServices().saveOrUpdate(users);
                }
                break;
                case "6": {
                    profileServices.getUserServices().deleteUser(users);
                    return "redirect:/logout";
                }
                default:
                    return "redirect:/profile";
            }
        } else {
            model.addAttribute("error", faliure);
            return "profile";
        }
        return "redirect:/profile";
    }

    /*@GetMapping("/facebook/delete")                               CANT ADD SAME USER AGAIN AFTER DELETE
    @PreAuthorize("hasRole('ROLE_FACEBOOK')")
    public String deleteFb(HttpServletRequest request) {
        profileServices.getUserServices().deleteUser(profileServices.getUserServices().getByName(request.getUserPrincipal().getName()));
        return "redirect:/logout";
    }*/
}
