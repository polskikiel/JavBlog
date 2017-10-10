package pl.blog.services.io;

import pl.blog.domain.Users;

import java.util.List;
import java.util.Map;

/**
 * Created by Mike on 08.08.2017.
 */
public interface UserServices {
    List<Users> listAll();

    public List<Users> searchUsers(String search);

    Users getById(Long id);

    Users getByName(String name);

    Users getByMail(String mail);

    Users saveOrUpdate(Users product);

    void delete(Long id);

    void deleteUser(Users users);

    Long getIdFromUsername(String username);

}
