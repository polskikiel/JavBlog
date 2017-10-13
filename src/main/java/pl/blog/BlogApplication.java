package pl.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import pl.blog.services.AsyncServices;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)		// enables annotations like @Order & @Secured
public class BlogApplication /*implements CommandLineRunner*/ {
	@Autowired
	AsyncServices asyncServices;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}


	//comment for tests
	/*@Override
	@Transactional
	public void run(String... strings) throws Exception {
		while (true) {
			asyncServices.clearCache();
			try {
				Thread.sleep(300000);
			} catch (InterruptedException ie) {
				throw new InterruptedException("ClearCache interrupted");
			}
		}
	}*/
}
