package com.sam.sap_commons.mq;

import com.sam.sap_commons.exchange.ApiMq;
import com.sam.sap_commons.utils.AjaxUtils;
import com.sam.sap_commons.utils.JsonUtil;
import com.sam.sap_commons.utils.SysDefaults;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqCommonProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String queue, String mqBody) {
        // 设置消息对象里的traceId
        ApiMq apiMq = ApiMq.builder()
                .mqType(queue)
                .mqBody(mqBody)
                .timestamp(SysDefaults.now())
                .build();
        apiMq.setTraceId(AjaxUtils.getTraceId());
        this.sendMsg(apiMq);
    }

    public void sendMsg(ApiMq apiMq) {
        // 设置消息对象里的traceId
        apiMq.setTraceId(AjaxUtils.getTraceId());
        log.info("MQ: sending msg {}", apiMq);
        this.rabbitTemplate.convertAndSend(apiMq.getMqType(), JsonUtil.toJsonString(apiMq));
        // 移除
        AjaxUtils.removeTraceId();
    }
}
