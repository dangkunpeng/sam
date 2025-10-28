package com.sam.sam_msg.mq;

import com.sam.sap_commons.utils.SysDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
//@Profile({"prd", "uat"})
@Component
public class RabbitMqConfig {
    private static final String DLX_EXCHANGE = "dlx.exchange";
    private static final String DLX_QUEUE = "dlx.queue";
    public static final String DLX_ROUTING_KEY = "dlx.routing.key";

    @Bean
    public Queue mqMain() {
        return new Queue(SysDefaults.MQ_MAIN);
    }

    @Bean
    public Queue mqMail() {
        return new Queue(SysDefaults.MQ_MAIL);
    }


    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }


    // 死信交换器
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    // 死信队列
    @Bean
    public Queue dlxQueue() {
        return new Queue(DLX_QUEUE, true);
    }

    // 绑定死信队列
    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue())
                .to(dlxExchange())
                .with("dlx.routing.key");
    }

    // 业务队列1 - 带TTL
    @Bean
    public Queue businessQueueWithTTL() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        args.put("x-message-ttl", 60000); // 60秒TTL
        return new Queue("business.queue.ttl", true, false, false, args);
    }

    // 业务队列2 - 带最大长度
    @Bean
    public Queue businessQueueWithMaxLength() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        args.put("x-max-length", 10); // 最大10条消息
        return new Queue("business.queue.maxlength", true, false, false, args);
    }
}