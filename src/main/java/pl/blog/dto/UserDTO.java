package pl.blog.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * Created by Mike on 10.07.2017.
 */
public class UserDTO implements Serializable {

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 32, message = "Login jest niepoprawny")
    private String login;

    @Size(min = 6, max = 32, message = "Hasło niepoprawne")
    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    private int day;
    @NotNull
    private int month;
    @NotNull
    private int year;

    @NotNull
    @NotEmpty
    @Email(message = "Niepoprawny mail")
    private String email;

    @NotNull
    @NotEmpty
    @Email
    private String email2;

    @Size(max = 255, message = "Too long desc ;p")
    private String desc;
    @Size(max = 255)
    private String imgUrl;


    @Override
    public String toString() {
        return "UserDTO{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", email='" + email + '\'' +
                ", email2='" + email2 + '\'' +
                ", desc='" + desc + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean emailComparison() {
        return email == email2;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
