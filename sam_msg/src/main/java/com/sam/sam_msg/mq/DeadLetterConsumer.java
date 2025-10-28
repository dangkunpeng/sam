package com.sam.sam_msg.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.sam.sam_msg.mq.RabbitMqConfig.DLX_QUEUE;

@Component
public class DeadLetterConsumer {

    @RabbitListener(queues = DLX_QUEUE)
    public void handleDeadLetterMessage(String message) {
        System.out.println("Received dead letter message: " + message);
        // 处理死信消息的逻辑
    }
}