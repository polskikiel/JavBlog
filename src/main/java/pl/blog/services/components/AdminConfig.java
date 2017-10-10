package pl.blog.services.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import pl.blog.domain.Role;
import pl.blog.domain.Users;
import pl.blog.repos.UserRepo;
import pl.blog.services.UserServicesImpl;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Set;

/**
 * Created by Mike on 01.07.2017.
 */
@Component
public class AdminConfig {
    @Autowired
    UserRepo userRepo;
    @Autowired
    Users adminUser;

    @PostConstruct
    public void addAdmin(){
        userRepo.save(adminUser);
    }
}
