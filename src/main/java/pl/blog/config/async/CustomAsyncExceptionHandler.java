package pl.blog.config.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import sun.nio.cs.ext.IBM037;

import java.lang.reflect.Method;

public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        System.out.println("Exception message - " + throwable.getMessage());
        System.out.println("Method name - " + method.getName());
        for (final Object param : objects) {
            System.out.println("Param - " + param);
        }
    }
}
