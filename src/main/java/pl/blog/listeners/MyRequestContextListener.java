package pl.blog.listeners;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.annotation.WebListener;

@Configuration
@WebListener
public class MyRequestContextListener extends RequestContextListener {      //  ( must have to use session & request Scope beans ) !!!!!
}
