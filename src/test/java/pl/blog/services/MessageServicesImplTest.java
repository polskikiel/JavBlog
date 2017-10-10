package pl.blog.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.blog.domain.Message;
import pl.blog.domain.Users;
import pl.blog.repos.MessagesRepo;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by Mike on 23.08.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MessageServicesImplTest {
    @Autowired
    MessageServicesImpl services;

    private Users users;
    private Users users1;
    private Users testUser;

    @Before
    public void init() {
        users = new Users("TESTER", "TEST@gmail.com", LocalDate.now(), "");
        users1 = new Users("TESTER1", "TEST1@gmail.com", LocalDate.now(), "");
        services.getUserServices().saveOrUpdate(users);
        services.getUserServices().saveOrUpdate(users1);

        testUser = services.getUserServices().getByName("PolskiKiel");
    }

    @Test
    public void countNewMessages() throws Exception {
        Assert.assertTrue(services.countNewMessages("PolskiKiel") == 0);

        services.addWelcomeMsg("PolskiKiel");       // welcome msg test

        Assert.assertTrue(services.countNewMessages("PolskiKiel") == 1);
    }


    @Test
    public void lastMessageWithContact() throws Exception {
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG1", "TITLE1");
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG2", "TITLE2");
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG3", "TITLE3");

        Assert.assertTrue(services.lastMessageWithContact("TESTER", testUser.getId()).getBody().equals("MSG3"));
    }

    @Test
    public void lastMessages() throws Exception {
    }

    @Test
    public void setOpened() throws Exception {
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG1", "TITLE1");
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG2", "TITLE2");
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG3", "TITLE3");

        for (Message message : services.listAll(users.getUsername())) {
            Assert.assertFalse(message.isMessageOpened());
        }
        services.setOpened(services.listAll(users.getUsername()), testUser.getUsername());
        for (Message message : services.listAll(users.getUsername())) {
           Assert.assertTrue(message.isMessageOpened());
        }
    }

    @Test
    public void listAllMessagesFromUserWithId() throws Exception {
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG1", "TITLE1");
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG2", "TITLE2");
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG3", "TITLE3");
        Assert.assertTrue(services.listAllMessagesFromUserWithId("TESTER", testUser.getId()).size() == 3);
    }

    @Test
    public void listEachContact() throws Exception {
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG1", "TITLE1");
        Assert.assertTrue(services.listEachContact("TESTER").size() == 1);

        services.sendMessage(users1.getId(), users.getUsername(), "MSG1", "TITLE1");
        Assert.assertTrue(services.listEachContact("TESTER").size() == 2);

    }

    @Test
    public void listAll() throws Exception {
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG1", "TITLE1");
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG2", "TITLE2");
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG3", "TITLE3");

        Assert.assertTrue(services.listAll("TESTER").size() == 3);
    }

    @Test
    public void htmlMsg() throws Exception {
    }


    @Test
    public void userMsgGetById() throws Exception {
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG1", "TITLE1");
        Assert.assertNotNull(services.userMsgGetById(users.getId(), 0L));
        Assert.assertTrue(services.userMsgGetById(users.getId(), 0L).getBody().equals("MSG1"));
    }

    @Test
    public void cryptMsg() throws Exception {
    }

    @Test
    public void delete() throws Exception {
        services.sendMessage(testUser.getId(), users.getUsername(), "MSG1", "TITLE1");

    }

    @Test
    public void userMsgDelete() throws Exception {
    }

    @Test
    public void sendMessage() throws Exception {
    }

}