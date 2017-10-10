package pl.blog.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import pl.blog.converters.TextToHTML;
import pl.blog.domain.Message;
import pl.blog.domain.Users;
import pl.blog.repos.MessagesRepo;
import pl.blog.services.io.MessageServices;
import pl.blog.services.io.UserServices;

import java.util.*;

/**
 * Created by Mike on 10.08.2017.
 */
@Service
public class MessageServicesImpl implements MessageServices {

    private MessagesRepo messagesRepo;
    private UserServices userServices;
    private Users msgBot;
    private Date requestDate;

    final static Logger logger = Logger.getLogger(MessageServicesImpl.class);
    @Autowired
    public MessageServicesImpl(MessagesRepo messagesRepo, UserServices userServices, Users msgBot, Date requestDate) {
        this.messagesRepo = messagesRepo;
        this.userServices = userServices;
        this.msgBot = msgBot;
        this.requestDate = requestDate;
    }


    public MessageServicesImpl() {
    }

    public int countNewMessages(String username) {
        int count = 0;
        for (Message message :
                userServices.getByName(username).getRecivedMessages()) {
            if (!message.isMessageOpened()) {
                count++;
            }
        }
        return count;
    }

    public void addWelcomeMsg(Users user) {
        sendMessage(user.getId(), msgBot.getUsername(), "Welcome on my page." +
                "Now after you successful login you can customize your profile and create your own article." +
                "Have fun!", "Hello sir!");
    }
    public void addWelcomeMsg(String username) {
        sendMessage(getUserServices().getByName(username).getId(), msgBot.getUsername(), "Welcome on my page." +
                "Now after you successful login you can customize your profile and create your own article." +
                "Have fun!", "Hello sir!");
    }

    public Message lastMessageWithContact(String username, Long id) {
        List<Message> messages = listAllMessagesFromUserWithId(username, id);
        return messages.get(messages.size() - 1);
    }

    public Map<String, Message> lastMessages(String username) {
        List<Users> users = listEachContact(username);
        Map<String, Message> messages = new HashMap<>();

        for (Users user : users) {
            messages.put(user.getUsername() , lastMessageWithContact(username, user.getId()));
        }

        return messages;
    }

    public void setOpened(List<Message> messages, String name) {
        for (Message message : messages) {
            if (message.getAddressee().getUsername().equals(name)) {
                message.setMessageOpened(true);
            }
            messagesRepo.save(message);
        }
    }

    public List<Message> listAllMessagesFromUserWithId(String username, Long id) {
        List<Message> messages = listAll(username);
        List<Message> messagesWith = new ArrayList<>();

        Users addressee = userServices.getById(id);
        if (addressee.getUsername().equals(username)) {         // if user want to send message to himself
            for (Message message : messages) {
                if (message.getAuthor().getUsername().equals(addressee.getUsername())) {
                    messagesWith.add(message);
                }
            }
        } else {
            for (Message message : messages) {
                if (message.getAuthor().getUsername().equals(addressee.getUsername()) ||
                        message.getAddressee().getUsername().equals(addressee.getUsername())) {        // save message to list IF user(id) send it to you, or you send message to him

                    messagesWith.add(message);
                }
            }
        }

        Collections.sort(messagesWith);         // sorting by date
        return messagesWith;
    }


    @Override
    public List<Users> listEachContact(String username) {
        List<Users> users = new ArrayList<>();

        List<Message> messageList = listAll(username);

        Users user = userServices.getByName(username);


        for (Message message : messageList) {
            if (!users.contains(message.getAddressee()) && !message.getAddressee().getUsername().equals(user.getUsername())) {
                users.add(message.getAddressee());
            }
            if (!users.contains(message.getAuthor()) && !message.getAuthor().getUsername().equals(user.getUsername())) {
                users.add(message.getAuthor());
            }
        }
        return users;

    }

    @Override
    public List<Message> listAll(String username) {
        List<Message> messages = new ArrayList<>();

        if (userServices.getByName(username) != null) {
            messages.addAll(userServices.getByName(username).getMessages());
            messages.addAll(userServices.getByName(username).getRecivedMessages());
        }
        return messages;
    }

    public List<Message> htmlMsg(List<Message> messages) {
        for (Message message : messages) {
            message.setBody(TextToHTML.htmlText(message.getBody()));
        }
        return messages;
    }

    @Override
    public List<Message> listAll(Long userId) {
        List<Message> messages = new ArrayList<>();

        if (userServices.getById(userId) != null) {
            messages.addAll(userServices.getById(userId).getMessages());
            messages.addAll(userServices.getById(userId).getRecivedMessages());
        }
        return messages;
    }

    @Override
    public Message getById(Long id) {

        return messagesRepo.findOne(id);
    }

    @Override
    public Message userMsgGetById(Long userId, Long id) {
        return userServices.getById(userId).getMessages().get(id.intValue());
    }

    @Override
    public String cryptMsg(String message) {
        return BCrypt.hashpw(message, BCrypt.gensalt(11));
    }

    @Override
    public void delete(Long id) {
        messagesRepo.delete(id);
    }

    @Override
    public void userMsgDelete(Long userId, Long id) {
        messagesRepo.delete(userServices.getById(userId).getMessages().get(id.intValue()));

    }

    @Override
    public void sendMessage(Long address_id, String username, String message, String title) {
        Users author = userServices.getByName(username);
        Users addressee = userServices.getById(address_id);

        author.setMessageCount(author.getMessageCount() + 1);
        addressee.setMessageCount(author.getMessageCount() + 1);

        Message message1 = new Message(message, title);

        message1.setAddressee(addressee);
        message1.setAuthor(author);
        message1.setDate(requestDate);
        message1.setMessageReceived(true);

        //logger.info("Sending message");


        List<Message> messages = author.getMessages();
        messages.add(message1);
        author.setMessages(messages);

        messages =  addressee.getRecivedMessages();
        messages.add(message1);
        addressee.setRecivedMessages(messages);

        messagesRepo.save(message1);
        userServices.saveOrUpdate(author);
        userServices.saveOrUpdate(addressee);
    }

    @Override
    public UserServices getUserServices() {
        return userServices;
    }

}
