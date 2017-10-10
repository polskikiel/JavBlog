package pl.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.blog.domain.Message;
import pl.blog.domain.Users;
import pl.blog.dto.MessageDTO;
import pl.blog.dto.profileEdits.TextDTO;
import pl.blog.services.MessageServicesImpl;
import pl.blog.services.io.MessageServices;
import pl.blog.services.io.UserServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 10.08.2017.
 */
@Controller
public class MessageController {
    @Autowired
    MessageServicesImpl messageServices;


    @GetMapping("/message*")
    public String getMessage(HttpServletRequest http, Model model, HttpSession session,
                             @RequestParam(value = "id", required = false) Long id) {

        String name = http.getUserPrincipal().getName();

        model.addAttribute("message", new MessageDTO());
        model.addAttribute("text", new TextDTO());

        model.addAttribute("user",
                messageServices.getUserServices().getByName(name));

        model.addAttribute("lastMessage",
                messageServices.lastMessages(name));

        model.addAttribute("contacts",
                messageServices.listEachContact(name));


        if (id == null) {

        } else {

            List<Message> messages = messageServices.listAllMessagesFromUserWithId(name, id);

            model.addAttribute("addressee",
                    messageServices.getUserServices().getById(id));
            model.addAttribute("messagesWith", messages);

            messageServices.setOpened(messages, name);

            session.setAttribute("newMsg", messageServices.countNewMessages(name));
        }
        return "message";
    }

    @PostMapping("/messages*")
    public String postMessage(Model model, HttpServletRequest request,
                              @ModelAttribute("message") MessageDTO messageDTO,
                              @RequestParam(value = "id", required = false) Long id) {
        String name = request.getUserPrincipal().getName();
        System.out.println(messageDTO.getMessage());

        model.addAttribute("message", new MessageDTO());
        model.addAttribute("text", new TextDTO());

        model.addAttribute("user",
                messageServices.getUserServices().getByName(name));

        model.addAttribute("lastMessage",
                messageServices.lastMessages(name));

        model.addAttribute("contacts",
                messageServices.listEachContact(name));

        if (id != null) {
            messageServices.sendMessage(id, name, messageDTO.getMessage(), messageDTO.getTitle());
            return "redirect:/messages?id=" + id;
        } else {
            if (messageDTO.getSearch() != null) {
                System.out.println(messageDTO.getSearch());
                List<Users> users = messageServices.getUserServices().searchUsers(messageDTO.getSearch());
                if (users != null) {
                    model.addAttribute("searchUser", users);
                } else
                    model.addAttribute("searchError", "We could't find anyone");
                }

            }

            return "message";
        }

}

