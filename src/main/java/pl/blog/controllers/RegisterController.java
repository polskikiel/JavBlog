package pl.blog.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.blog.domain.Users;
import pl.blog.dto.UserDTO;
import pl.blog.repos.UserRepo;
import pl.blog.services.RegistrationService;
import pl.blog.services.UserServicesImpl;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


/**
 * Created by Mike on 10.07.2017.
 */
@Controller
public class RegisterController {
    @Autowired
    RegistrationService services;

    final static Logger logger = Logger.getLogger(RegistrationService.class);



    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("thisyear", services.getYear() + 1);
        model.addAttribute("months", services.monthsToList());
        model.addAttribute("img", services.getImgList());
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("user") @Valid UserDTO user, BindingResult result, Model model) {
        Map<String, String> faliures = services.faliures(user);

        model.addAttribute("user", new UserDTO());
        model.addAttribute("thisyear", services.getYear() + 1);
        model.addAttribute("months", services.monthsToList());
        model.addAttribute("img", services.getImgList());


        if (result.hasErrors() | !faliures.isEmpty()) {
            model.addAttribute("faliure", faliures);

            logger.info("Registration failed");
            return "/register";
        } else {
            Users user1 = new Users(user.getLogin(),
                    BCrypt.hashpw(user.getPassword(),
                            BCrypt.gensalt(11)),
                    user.getEmail(),
                    LocalDate.of(user.getYear(),
                            user.getMonth(),
                            user.getDay()));


            user1.setRoles(UserServicesImpl.getUserAuth());

            if (user.getDesc() != null)                     // setting description
                user1.setDescription(user.getDesc());

            if (!user.getImgUrl().isEmpty()) {              // setting imgUrl
                user1.setImgUrl(user.getImgUrl());
            } else {
                user1.setImgUrl("https://cdn1.iconfinder.com/data/icons/freeline/32/account_friend_human_man_member_person_profile_user_users-256.png");            // default profile photo
            }

            services.getMessageServices().getUserServices().saveOrUpdate(user1);
            services.getMessageServices().addWelcomeMsg(user1);

            logger.info("new User - " + user);
            return "redirect:/";
        }
    }
}
