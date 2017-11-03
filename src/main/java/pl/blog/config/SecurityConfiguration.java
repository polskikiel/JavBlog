package pl.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import pl.blog.config.facebook.FacebookSignInAdapter;
import pl.blog.services.user.security.CustomUserDetailsService;

import javax.sql.DataSource;

/**
 * Created by Mike on 26.07.2017.
 */
@Configuration
@EnableWebSecurity
@ComponentScan("pl.blog")
public class SecurityConfiguration {

    @Configuration
    @Order(1)   // by setting order we can make multiple security settings and we always can choose which we want to use
    public static class ApiWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private CustomUserDetailsService userDetailsService;
        private ConnectionFactoryLocator connectionFactoryLocator;
        private UsersConnectionRepository usersConnectionRepository;
        private ConnectionSignUp facebookConnectionSignup;
        private DataSource dataSource;

        @Autowired
        public ApiWebSecurityConfigurerAdapter(CustomUserDetailsService userDetailsService, ConnectionFactoryLocator connectionFactoryLocator,
                                               UsersConnectionRepository usersConnectionRepository, ConnectionSignUp facebookConnectionSignup, DataSource dataSource) {
            this.userDetailsService = userDetailsService;
            this.connectionFactoryLocator = connectionFactoryLocator;
            this.usersConnectionRepository = usersConnectionRepository;
            this.facebookConnectionSignup = facebookConnectionSignup;
            this.dataSource = dataSource;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.
                    authorizeRequests().
                    antMatchers("/", "/resources*//**", "/css*//**", "/images*//**", "/font*//**", "/register", "/login", "/connect/*", "/signin/**", "/signup/**", "/favicon.ico", "/js*//**").
                    permitAll().
                    anyRequest().
                    authenticated().
                    and().
                    authorizeRequests().
                    antMatchers("/newarticle", "/profile**", "/message**", "/article**").
                    access("hasRole('ROLE_USER')").
                    and().
                    formLogin().
                    loginPage("/login").
                    passwordParameter("password").usernameParameter("username").
                    failureUrl("/login?error").
                    and().
                    logout().logoutUrl("/logout").
                    logoutSuccessUrl("/login?logout").
                    and().
                    exceptionHandling().accessDeniedPage("/noaccess").
                    and().
                    csrf().csrfTokenRepository(csrfTokenRepository()).  // token cache
                    and().
                    rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(604800);     // setting remember-me cache on 1 week (in seconds)

        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder(11);     // hashing passwords
        }

        private CsrfTokenRepository csrfTokenRepository() {         // must to do if using forms as user
            HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
            repository.setSessionAttributeName("_csrf");
            return repository;
        }

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
            db.setDataSource(dataSource);
            return db;
        }

        @Bean
        public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
            SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
            auth.setTargetUrlParameter("/");
            return auth;
        }

        @Bean
        public ProviderSignInController providerSignInController() {
            ((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignup);

            return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository,
                    new FacebookSignInAdapter());
        }


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }
    }
}
