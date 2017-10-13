package pl.blog.services.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.blog.domain.Users;
import pl.blog.repos.UserRepo;

import javax.annotation.PostConstruct;

/**
 * Created by Mike on 15.08.2017.
 */
@Component
public class BasicUsersCreator {
    private Users msgBot;
    private UserRepo userRepo;

    @Autowired
    public BasicUsersCreator(Users msgBot, UserRepo userRepo) {
        this.msgBot = msgBot;
        this.userRepo = userRepo;
    }

    @PostConstruct
    public void createBot() {
        userRepo.save(msgBot);
    }
}
