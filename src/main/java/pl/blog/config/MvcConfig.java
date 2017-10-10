package pl.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import pl.blog.interceptors.ConnectionManagement;
import pl.blog.interceptors.SessionManagement;

/**
 * Created by Mike on 01.07.2017.
 */
@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
    SessionManagement sessionManagement;
    ConnectionManagement connectionManagement;

    @Autowired
    public MvcConfig(SessionManagement sessionManagement, ConnectionManagement connectionManagement) {
        this.sessionManagement = sessionManagement;
        this.connectionManagement = connectionManagement;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("main");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/newarticle").setViewName("newarticle");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/profile").setViewName("profile");
        registry.addViewController("/message").setViewName("message");
        registry.addViewController("/noaccess").setViewName("403");
        registry.addViewController("/article").setViewName("article");
        registry.addViewController("/articles").setViewName("articles");

    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.setOrder(Integer.MIN_VALUE);
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/js/**").addResourceLocations("../webapp/resources/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("../webapp/resources/css/");
        registry.addResourceHandler("/font/**").addResourceLocations("../webapp/resources/font/");
        registry.addResourceHandler("/images/**").addResourceLocations("../webapp/resources/images/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("src/main/webapp/resources/images/favicon.ico");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionManagement)
                .addPathPatterns("/**")
                .excludePathPatterns("/resources*");

        registry.addInterceptor(connectionManagement)
                .addPathPatterns("/**")
                .excludePathPatterns("/resources*");
    }
}
