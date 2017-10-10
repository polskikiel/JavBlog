package pl.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import pl.blog.dto.profileEdits.PassDTO;
import pl.blog.dto.profileEdits.TextDTO;
import pl.blog.services.io.UserServices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mike on 05.08.2017.
 */
@Service
public class ProfileServices {

    private PassDTO passDTO;
    private TextDTO textDTO;

    public ProfileServices(PassDTO passDTO, TextDTO textDTO) {
        this.passDTO = passDTO;
        this.textDTO = textDTO;
    }

    public ProfileServices() {
    }

    @Autowired
    public ProfileServices(UserServices userServices, ArticleServices articleServices) {
        this.userServices = userServices;
        this.articleServices = articleServices;
    }

    private UserServices userServices;
    private ArticleServices articleServices;

    static public String getAttribute(String edit) {
        switch (edit) {
            case "0": {
                return "Profile photo edit";
            }
            case "1": {
                return "Nick edit";
            }
            case "2": {
                return "Password edit";
            }
            case "3": {
                return "Email edit";
            }
            case "4": {
                return "Description edit";
            }
            case "5": {
                return "Messages";
            }
            case "6": {
                return "Delete account";
            }
        }
        return "";
    }

    public String faliure(String option, String pass) {
        switch (option) {
            case "0": {
                if (textDTO.getText().isEmpty()) {
                    return "Enter an url";
                }
                if (textDTO.getText().length() > 255) {
                    return "Given url is too long";
                }
                return "";
            }
            case "1": {
                if (!textDTO.getText().isEmpty() && textDTO.getText().length() > 5 && textDTO.getText().length() < 20) {
                    return "";
                } else
                    return "Nick should contain 6-20 letters";
            }
            case "2": {
                if (pass != null) {
                    if (BCrypt.checkpw(passDTO.getPass(), pass)) {
                        if (passDTO.getPass1().equals(passDTO.getPass2())) {
                            Pattern pattern = Pattern.compile("((?=.*\\d)[a-zA-Z].{6,20})");
                            Matcher matcher = pattern.matcher(passDTO.getPass1());

                            if (matcher.matches()) {
                                return "";
                            } else
                                return "Password should contain 6-20 letters with at least one digit";
                        } else
                            return "Passwords weren't the same";
                    } else
                        return "Your password is different";

                } else
                    return "Enter a password";
            }
            case "3": {
                if (!textDTO.getText().isEmpty()) {
                    Pattern pattern = Pattern.compile("[a-zA-Z]{4,32}@[a-zA-Z]{1,32}.[a-zA-Z]{2,3}");
                    Matcher matcher = pattern.matcher(textDTO.getText());
                    if (matcher.matches()) {
                        return "";
                    } else
                        return "Given email are wrong";
                } else
                    return "Enter an email";
            }
            case "4": {
                if (!textDTO.getText().isEmpty()) {
                    if (textDTO.getText().length() < 255) {
                        return "";
                    } else
                        return "Description should contain max 255 chars";
                } else
                    return "Enter a description";

            }
            case "5": {

            }
            case "6": {
                if (pass != null || !pass.isEmpty()) {
                    if (BCrypt.checkpw(textDTO.getText(), pass)) {
                        return "";
                    } else
                        return "Your password is different";

                } else
                    return "Enter a password";
            }
        }
        return "";
    }

    public PassDTO getPassDTO() {
        return passDTO;
    }

    public void setPassDTO(PassDTO passDTO) {
        this.passDTO = passDTO;
    }

    public TextDTO getTextDTO() {
        return textDTO;
    }

    public void setTextDTO(TextDTO textDTO) {
        this.textDTO = textDTO;
    }

    public UserServices getUserServices() {
        return userServices;
    }

    public void setUserServices(UserServices userServices) {
        this.userServices = userServices;
    }

    public ArticleServices getArticleServices() {
        return articleServices;
    }
}
