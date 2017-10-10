package pl.blog.domain.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.blog.dto.UserDTO;
import pl.blog.services.MessageServicesImpl;
import pl.blog.services.RegistrationService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by Mike on 12.07.2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {
    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public RegistrationService registrationService() {
            return new RegistrationService();
        }
    }
    @Autowired
    private RegistrationService registrationService;

    @Test
    public void emailComparision(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("PolskiKiel@gmail.com");
        Assert.assertFalse(registrationService.emailComparison(userDTO));

        userDTO.setEmail2("PolskiKiel@gmail.com");
        Assert.assertTrue(registrationService.emailComparison(userDTO));

        userDTO.setEmail2("sdxzc");
        Assert.assertFalse(registrationService.emailComparison(userDTO));
    }

    @Test
    public void monthsToList(){
        List<String> list = registrationService.monthsToList();
        Assert.assertEquals(12, list.size());
    }
    @Test
    public void faliures(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail2("PolskiKiel@gmail.com");
        userDTO.setEmail("PolskiKiel@gmail.com");
        userDTO.setLogin("ku[apap");
        userDTO.setPassword("stubidi92");
        userDTO.setDay(12);
        userDTO.setMonth(11);
        userDTO.setYear(1997);
        userDTO.setDesc("XD");

        Map<String, String> map = registrationService.faliures(userDTO);
        Assert.assertTrue(map.size() == 1);

        userDTO.setLogin("Bajkopisarz12");

        map = registrationService.faliures(userDTO);
        Assert.assertTrue(map.size() == 0);

    }
}
