package pl.blog.config;

/**
 * Created by Mike on 07.08.2017.
 */
//@Configuration
//@ComponentScan("pl.blog.services.components")
//@EnableRabbit
public class RabbitConfig {
    public static final String queueName = "blog";
//    @Bean
//    Queue queue() {
//        return new Queue(queueName, false);
//    }
//
//    @Bean
//    TopicExchange topicExchange() {
//        return new TopicExchange(queueName + "-exchange");
//    }
//
//    @Bean
//    Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(queueName);
//    }
//
//    @Bean
//    SimpleMessageListenerContainer container(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory,
//                                             MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(queueName);
//        container.setMessageListener(listenerAdapter);  // if we want to use rabbit on real server we need to set uri
//
//        return container;
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver){
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }



}
