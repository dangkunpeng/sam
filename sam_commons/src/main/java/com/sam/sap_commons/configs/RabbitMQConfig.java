package com.sam.sap_commons.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    private static final String DLX_EXCHANGE = "dlx.exchange";
    private static final String DLX_QUEUE = "dlx.queue";
    public static final String DLX_ROUTING_KEY = "dlx.routing.key";

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