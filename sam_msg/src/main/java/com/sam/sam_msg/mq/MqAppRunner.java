package com.sam.sam_msg.mq;

import com.google.common.collect.Lists;
import com.rabbitmq.client.Channel;
import com.sam.sap_commons.utils.SysDefaults;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
//@Profile({"prd", "uat"})
@Component
public class MqAppRunner implements ApplicationRunner {

//    @Bean
//    public Queue mqMain() {
//        return new Queue(SysDefaults.MQ_MAIN);
//    }
//
//    @Bean
//    public Queue mqMail() {
//        return new Queue(SysDefaults.MQ_MAIL);
//    }

    @Resource
    private AmqpAdmin amqpAdmin;
//    @Resource
//    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(ApplicationArguments args) {

        Connection connection = this.rabbitTemplate.getConnectionFactory().createConnection();
        Channel channel = connection.createChannel(true);
        // 消息队列可以储存到数据库或者配置文件里
        List<String> consumerIds = Lists.newArrayList(SysDefaults.MQ_MAIN, SysDefaults.MQ_MAIL);
        for (String consumerId : consumerIds) {
            // 开启队列
            startQueue(consumerId);
//            // 开启消费者
//            startConsumer(consumerId);
        }
    }

    /**
     * 开启队列
     *
     * @param queueName
     */
    private void startQueue(String queueName) {
        // 参数：队列名，是否持久化
        Queue queue = new Queue(queueName, true);
        QueueInformation queueInformation = this.amqpAdmin.getQueueInfo(queueName);
        if (StringUtils.isBlank(queueInformation.getName())) {
            log.info("start queue {}", queueName);
            this.amqpAdmin.declareQueue(queue);
        }
    }

//    /**
//     * 开启/关闭单个消费者
//     */
//    private void startConsumer(String consumerId) {
//        MessageListenerContainer messageListenerContainer = rabbitListenerEndpointRegistry.getListenerContainer(consumerId);
//        if (messageListenerContainer.isRunning()) {
//            return;
//        }
//        // 开启消费者
//        messageListenerContainer.start();
//    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}