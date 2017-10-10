package pl.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import pl.blog.services.AsyncServices;

import javax.transaction.Transactional;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)		// enables annotations like @Order & @Secured
public class BlogApplication /*implements CommandLineRunner*/ {
	@Autowired
	AsyncServices asyncServices;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	/*@Override
	@Transactional
	public void run(String... strings) throws Exception {
		boolean flag = true;
		while (flag) {
			asyncServices.clearCache();
			try {
				Thread.sleep(300000);
			} catch (InterruptedException ie) {
				flag = false;

			}
		}
	}*/
}
