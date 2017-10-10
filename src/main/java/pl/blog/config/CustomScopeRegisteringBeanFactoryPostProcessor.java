package pl.blog.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.SimpleThreadScope;

/**
 * Created by Majk on 2017-10-05.
 */
public class CustomScopeRegisteringBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        configurableListableBeanFactory.registerScope("session", new SimpleThreadScope());  // here we register scopes to use in tests
        configurableListableBeanFactory.registerScope("request", new SimpleThreadScope());
    }
}
