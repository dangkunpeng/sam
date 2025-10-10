package com.sam.sap_commons.mq;

import com.sam.sap_commons.exchange.ApiMq;
import com.sam.sap_commons.utils.AjaxUtils;
import com.sam.sap_commons.utils.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqCommonProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(ApiMq msg) {
        // 设置消息对象里的traceId
        msg.setTraceId(AjaxUtils.getTraceId());
        log.info("MQ: sending msg {}", msg);
        this.rabbitTemplate.convertAndSend(msg.getMqType(), JsonUtil.toJsonString(msg));
    }
}
