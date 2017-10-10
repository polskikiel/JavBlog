package pl.blog.config.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.stereotype.Service;
import pl.blog.domain.Users;
import pl.blog.repos.UserRepo;
import pl.blog.services.RegistrationService;
import pl.blog.services.io.UserServices;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp{
    @Autowired
    private RegistrationService registrationService;

    @Override
    public String execute(Connection<?> connection) {
        Users users = new Users();
        users.setUsername(connection.getDisplayName());
        users.setPassword(BCrypt.hashpw(rndChar(9), BCrypt.gensalt(11)));
        users.setImgUrl(connection.getImageUrl());
        users.setMail(connection.fetchUserProfile().getEmail());        // returns null (?)

        registrationService.getMessageServices().getUserServices().saveOrUpdate(users);
        registrationService.getMessageServices().addWelcomeMsg(users);
        return users.getUsername();
    }

    private String rndChar (int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < n; i++) {
            int rnd = (int) (Math.random() * 52); // or use Random or whatever
            char base = (rnd < 26) ? 'A' : 'a';
            stringBuilder.append((char) (base + rnd % 26));
        }

        return stringBuilder.toString();
    }
}
