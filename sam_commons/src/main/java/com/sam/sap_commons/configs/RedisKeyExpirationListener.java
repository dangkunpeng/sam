package com.sam.sap_commons.configs;

import com.sam.sap_commons.utils.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        String value = "";
        if (RedisHelper.hasKey(expiredKey)) {
            value = RedisHelper.get(expiredKey, String.class);
        }
        log.info("过期:Key={}, pattern={}", expiredKey, value);
    }
}