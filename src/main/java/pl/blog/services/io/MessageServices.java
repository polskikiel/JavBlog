package pl.blog.services.io;

import pl.blog.domain.Message;
import pl.blog.domain.Users;

import java.util.List;

/**
 * Created by Mike on 10.08.2017.
 */
public interface MessageServices {
    List<Message> listAll(Long id);

    public List<Message> listAll(String username);

    public List<Users> listEachContact(String username);

    Message getById(Long id);

    Message userMsgGetById(Long userId, Long id);

    String cryptMsg(String message);

    void delete(Long id);

    void userMsgDelete(Long userId, Long id );

    void sendMessage(Long address_id, String username, String message, String title);

    public UserServices getUserServices();

}
