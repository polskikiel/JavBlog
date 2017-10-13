package pl.blog.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import pl.blog.converters.TextToHTML;
import pl.blog.domain.Message;
import pl.blog.domain.Role;
import pl.blog.domain.Users;
import pl.blog.repos.MessagesRepo;
import pl.blog.repos.UserRepo;
import pl.blog.services.io.UserServices;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Mike on 19.07.2017.
 */
@Service
@Transactional
public class UserServicesImpl implements UserServices {

    UserRepo userRepo;
    Users msgBot;

    @Autowired
    public UserServicesImpl(UserRepo userRepo, Users msgBot) {
        this.userRepo = userRepo;
        this.msgBot = msgBot;
    }

    final static Logger logger = Logger.getLogger(UserServicesImpl.class);

    static public Set<Role> getAdminAuth() {
        Set<Role> authorities = new HashSet<>();
        authorities.add(new Role("ADMIN"));
        authorities.add(new Role("USER"));
        return authorities;
    }

    static public Set<Role> getUserAuth() {
        Set<Role> authorities = new HashSet<>();
        authorities.add(new Role("USER"));
        return authorities;
    }

    public int getCount() {
        return (int)userRepo.count();
    }

    @Override
    public Long getIdFromUsername(String username) {
        if (userRepo.findUserByUsername(username).isPresent()) {
            return userRepo.findUserByUsername(username).get().getId();
        }
        return 0L;
    }

    @Override
    public void deleteUser(Users users) {
        userRepo.delete(users);
    }

    @Override
    public Users getByMail(String mail) {
        if (userRepo.findUserByMail(mail).isPresent())
            return userRepo.findUserByMail(mail).get();

        return null;
    }

    @Override
    public List<Users> searchUsers(String search) {
        List<Users> searchUsers = new ArrayList<>();
        List<Users> users = listAll();

        if (userRepo.findUserByUsername(search).isPresent()) {
            searchUsers.add(userRepo.findUserByUsername(search).get());
            return searchUsers;
        }

        for (Users user : users) {
            if (user.getUsername().contains(search) || user.getMail().contains(search)) {   // nick null possibility
                searchUsers.add(user);
            }
        }
        if (searchUsers.isEmpty()) {
            return null;
        }
        return searchUsers;
    }

    @Override
    public List<Users> listAll() {
        return userRepo.findAll();
    }

    @Override
    public Users getById(Long id) {
        return userRepo.findOne(id);
    }

    @Override
    public Users saveOrUpdate(Users users) {
        return userRepo.save(users);
    }

    @Override
    public void delete(Long id) {
        userRepo.delete(id);
    }


    @Override
    public Users getByName(String name) {
        Optional<Users> users = userRepo.findUserByUsername(name);
        if (users.isPresent()) {
            return users.get();
        }
        logger.warn("USER<" + name + "> NOT FOUND");
        return null;
    }
}
