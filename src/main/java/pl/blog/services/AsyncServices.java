package pl.blog.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.blog.domain.UserConnection;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class AsyncServices {
    private UserConnectionServices userConnectionServices;
    final static Logger logger = Logger.getLogger(UserServicesImpl.class);

    @Autowired
    public AsyncServices(UserConnectionServices userConnectionServices) {
        this.userConnectionServices = userConnectionServices;
    }

    @Async
    public void clearCache() throws InterruptedException {
        userConnectionServices.deleteList(userConnectionServices.connectionsToClear());
        logger.info("UserConnection cache cleared");
    }

}
