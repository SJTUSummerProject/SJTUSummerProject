package com.sjtusummerproject.commentmicroservice.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitCommentMQConfig {

    @Value("${rabbitmq.host}")
    String rabbitmqHost;
    @Value("${rabbitmq.port}")
    int rabbitmqPort;

    public final static String CommentEXCHANGE_NAME = "CommentExchange";

    public final static String CommentQUEUE_NAME = "CommentQueue";
    public final static String CommentROUTING_KEY = "Commentkey";

    public final static String ReplyCommentQUEUE_NAME = "ReplyCommentQueue";
    public final static String ReplyCommentROUTING_KEY = "ReplyCommentKey";

    public final static String ReplyReplyQUEUE_NAME = "ReplyReplyQueue";
    public final static String ReplyReplyROUTING_KEY = "ReplyReplyKey";
    // 创建队列
    @Bean
    public Queue commentQueue() {
        return new Queue(CommentQUEUE_NAME);
    }

    @Bean
    public Queue replyCommentQueue() {
        return new Queue(ReplyCommentQUEUE_NAME);
    }

    @Bean
    public Queue replyReplyQueue() {
        return new Queue(ReplyReplyQUEUE_NAME);
    }

    // 创建一个 topic 类型的交换器
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(CommentEXCHANGE_NAME);
    }

    // 使用路由键（routingKey）把队列（Queue）绑定到交换器（Exchange）

    @Bean
    public Binding bindingExchangeCommentQueue(Queue commentQueue, TopicExchange exchange) {
        return BindingBuilder.bind(commentQueue).to(exchange).with(CommentROUTING_KEY);
    }

    @Bean
    public Binding bindingExchangeReplyReplyQueue(Queue replyReplyQueue, TopicExchange exchange) {
        return BindingBuilder.bind(replyReplyQueue).to(exchange).with(ReplyCommentROUTING_KEY);
    }

    @Bean
    public Binding bindingExchangeReplyCommentQueue(Queue replyCommentQueue, TopicExchange exchange) {
        return BindingBuilder.bind(replyCommentQueue).to(exchange).with(ReplyCommentROUTING_KEY);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqHost, rabbitmqPort);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

}

