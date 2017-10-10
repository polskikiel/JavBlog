package pl.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.blog.domain.Users;
import pl.blog.dto.UserDTO;
import pl.blog.repos.UserRepo;
import pl.blog.services.io.UserServices;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mike on 12.07.2017.
 */
@Service
public class RegistrationService {
    private MessageServicesImpl messageServices;

    @Autowired
    public RegistrationService(MessageServicesImpl messageServices) {
        this.messageServices = messageServices;
    }

    public RegistrationService() {
    }

    public String[] imgList = {
            "http://m.ocdn.eu/_m/d3a01fdd8e71911ea3d4f1366bd0bb89,10,1.jpg",
            "http://i.iplsc.com/kadr-z-filmu-chlopaki-nie-placza-rez-olaf-lubaszenko/0004IKCZN02A8D4J-C122-F4.jpg",
            "http://img.szafa.pl/ubrania/0/023293018/1459199694/laska.jpg",
            "https://peopledotcom.files.wordpress.com/2017/04/kendall-jenner1.jpg?w=2000&h=1333"
    };

    private String[] monthsList =
            {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


    public int getYear() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);     // returns actual year
    }

     public List<String> monthsToList() {
        List<String> list = new ArrayList<>();
        for (String s : monthsList) {
            list.add(s);
        }
        return list;
    }

    public boolean emailComparison(UserDTO userDTO) {
        return userDTO.getEmail().equals(userDTO.getEmail2());
    }

    public Map<String, String> faliures(UserDTO userDTO) {
        Map<String, String> map = new HashMap<>();

        if (!emailComparison(userDTO))
            map.put("emailCompare", "<p class=\"faliure\">Emails were not the same</p>");

        if (messageServices.getUserServices().getByName(userDTO.getLogin()) != null) {
            map.put("userExist" ,"<p class=\"faliure\">User with that login already exist</p>");
        }

        if (messageServices.getUserServices().getByMail(userDTO.getEmail()) != null) {
            map.put("mailExist", "<p class=\"faliure\">User with that email already exist</p>");
        }

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]{4,32}@[a-zA-Z]{1,32}.[a-zA-Z]{2,3}");
        Matcher matcher = pattern.matcher(userDTO.getEmail());
        if (!matcher.matches() || userDTO.getEmail() == null || userDTO.getEmail().length() < 4 || userDTO.getEmail().length() > 32)
            map.put("email", "<p class=\"faliure\">Email is uncorrect</p>");

        pattern = Pattern.compile("[a-zA-Z0-9]{6,32}");
        matcher = pattern.matcher(userDTO.getLogin());
        if (!matcher.matches() || userDTO.getLogin() == null || userDTO.getLogin().length() < 6 || userDTO.getLogin().length() > 32)
            map.put("login", "<p class=\"faliure\">Login is uncorrect</p>");

        pattern = Pattern.compile("((?=.*\\d)[a-zA-Z].{6,20})");
        matcher = pattern.matcher(userDTO.getPassword());
        if (!matcher.matches() || userDTO.getPassword() == null)
            map.put("pass", "<p class=\"faliure\">Password should contain one digit</p>");

        if (getYear() - userDTO.getYear() < 13)
            map.put("age", "You're supposed to be at least 13yr old");


        return map;

    }

    public MessageServicesImpl getMessageServices() {
        return messageServices;
    }

    public void setMessageServices(MessageServicesImpl messageServices) {
        this.messageServices = messageServices;
    }

    public String[] getImgList() {
        return imgList;
    }

    public void setImgList(String[] imgList) {
        this.imgList = imgList;
    }

    public String[] getMonthsList() {
        return monthsList;
    }

    public void setMonthsList(String[] monthsList) {
        this.monthsList = monthsList;
    }

}
