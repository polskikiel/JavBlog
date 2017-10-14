package pl.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import pl.blog.domain.*;
import pl.blog.domain.Role;
import pl.blog.dto.ArticleDTO;
import pl.blog.services.UserServicesImpl;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by Mike on 01.07.2017.
 */
@Configuration
public class AppConfig {
    @Value("${spring.social.facebook.appId}")
    private String appId;
    @Value("${spring.social.facebook.appSecret}")
    private String appSecret;

    @Bean
    public Date date() {
        return new Date();
    }

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new CustomScopeRegisteringBeanFactoryPostProcessor();
    }


    @Bean
    public Users adminUser() {
        Users user = new Users("PolskiKiel", BCrypt.hashpw("pass", BCrypt.gensalt(11)), "PolskiKiel@gmail.com", LocalDate.of(1997, 4, 21));
        Set<Role> roles = UserServicesImpl.getAdminAuth();
        Assert.notEmpty(roles, "Lista autoryzacji użytkownika " + user.getUsername() + " pusta!");
        user.setRoles(roles);
        user.setImgUrl("http://1.fwcdn.pl/ph/90/63/9063/238289.1.jpg");
        user.setNick("TigerTooth");
        user.setDescription("Hi I'm creator of this site. My name is Michał Kempski and I'm from Katowice in Poland. I programing in java since 2017, fewer i was programming in C & C++." +
                "I study IT at Silesian University of Technology - second year incoming :)");
        return user;
    }

    @Bean
    Article customArticle() {                           // cant hold \n in String and use it in JS
        Article customArticle = new Article();

        customArticle.setUser(adminUser());
        customArticle.setHeader("Test article");
        customArticle.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus congue eget sapien nec luctus. Nulla congue tellus non ligula tempor, a pretium nibh rhoncus. Integer ut dolor metus. Pellentesque a nibh quam. Nullam accumsan dolor ac varius malesuada. Integer eu malesuada diam. Aenean ac scelerisque risus, vel tristique lacus. Ut non neque id justo feugiat fringilla. Donec mattis ac elit nec volutpat. Sed ut ligula ac leo sollicitudin feugiat vel vel tellus. Donec massa est, feugiat at lobortis sit amet, scelerisque a nibh. Nam sagittis, nunc ac hendrerit bibendum, nunc est efficitur mi, vel dapibus nisi magna eu elit. Pellentesque in tortor ipsum. Proin rhoncus imperdiet luctus. Cras turpis purus, auctor id finibus ac, finibus vitae ipsum. Sed condimentum efficitur lacus in vehicula." +
                "Maecenas quis arcu id felis hendrerit laoreet sed vel libero. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean dictum nulla in est convallis condimentum. Suspendisse feugiat magna sit amet lectus tempus, sit amet maximus ex feugiat. Vivamus id velit consectetur, volutpat nibh nec, ullamcorper erat. In eu laoreet nisi. Duis magna libero, sodales id pellentesque et, accumsan varius felis. Vestibulum blandit pretium semper." +
                "Pellentesque tellus dui, ornare non nibh quis, euismod ultrices lectus. Phasellus suscipit lacus eget tempor hendrerit. Suspendisse auctor auctor nibh. Etiam quis convallis nisl, ut facilisis arcu. Ut pulvinar auctor facilisis. Sed tempus erat at auctor fermentum. Cras tristique justo a neque tempus sollicitudin. Duis hendrerit mi sed tincidunt elementum. Quisque est tellus, consequat vel magna eu, sodales tempor nunc. Proin tincidunt a diam sed molestie. Phasellus sapien dui, pulvinar non euismod lobortis, suscipit ultricies ligula. Curabitur in lorem aliquet, dignissim ex eget, lobortis lorem. In consequat luctus nunc quis convallis." +
                "Aenean scelerisque mi vel arcu consectetur venenatis. Aliquam auctor ligula ante, id tempus eros aliquet a. Cras lobortis blandit elementum. Praesent sed quam urna. Mauris hendrerit augue urna, in tincidunt ipsum vehicula non. Nulla nisi ipsum, aliquam non dui vitae, congue interdum enim. In placerat sem at magna dapibus, id convallis turpis dictum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Maecenas dapibus metus tortor, in gravida leo vehicula at. Morbi id quam sit amet quam venenatis congue." +
                "Curabitur vitae pellentesque justo. Maecenas vel blandit diam. Donec eu dignissim purus. Cras dui urna, pulvinar nec diam vitae, finibus vulputate lectus. Cras ac pellentesque dui, eu consequat tortor. Ut non arcu sit amet diam viverra varius. Vestibulum ullamcorper tincidunt tortor nec vehicula. Integer viverra pharetra tincidunt. Aliquam id metus ac mauris finibus rhoncus. Sed tincidunt varius lorem non fringilla. Ut laoreet nibh vel dolor interdum, non consectetur quam ultrices. Duis hendrerit malesuada ex, non pulvinar ante convallis nec. Interdum et malesuada fames ac ante ipsum primis in faucibus. Fusce gravida vitae enim convallis iaculis. Proin malesuada ultrices enim, vel vehicula enim vulputate id.");
        customArticle.setImgUrl("https://timkadlec.com/test/mq-svg/kiwi-mq.svg");
        customArticle.setCategory(new String[]{"Spring", "Mvc", "Data", "Java"});

        return customArticle;
    }

    @Bean
    public org.springframework.web.servlet.ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);

        return resolver;
    }


    @Bean
    public Users msgBot() {
        return new Users("JavaBLOG", "javablog@gmail.com",
                LocalDate.now(), "http://1.bp.blogspot.com/-Nwld_LIBkio/VOv54RKMwEI/AAAAAAAAABc/fVOiPSyJasw/s1600/fraud-bot-home.png");
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Date requestDate() {
        return Calendar.getInstance().getTime();
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Date sessionDate() {
        return Calendar.getInstance().getTime();
    }

}
