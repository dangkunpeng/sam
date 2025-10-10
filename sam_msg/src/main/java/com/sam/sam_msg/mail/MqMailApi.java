package com.sam.sam_msg.mail;

import com.rabbitmq.client.Channel;
import com.sam.sam_msg.mq.MqApi;
import com.sam.sap_commons.exchange.ApiMail;
import com.sam.sap_commons.exchange.ApiMq;
import com.sam.sap_commons.mq.MqCommonProducer;
import com.sam.sap_commons.utils.JsonUtil;
import com.sam.sap_commons.utils.KeyTool;
import com.sam.sap_commons.utils.SysDefaults;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Slf4j
//@Profile({"prd", "uat"})
@Service
public class MqMailApi {

    @Resource
    private MqApi mqApi;

    @Resource
    private MailClient mailClient;

    @Resource
    private MqCommonProducer mqCommonProducer;

    /**
     * 发送异常mail
     *
     * @param errorInfo
     */
    public void sendMail(String errorInfo) {
        this.sendMail(errorInfo, "kunpeng.dang@accenture.cn");
    }

    /**
     * 发送异常mail
     *
     * @param errorInfo
     */
    public void sendMail(String errorInfo, String to) {
        this.sendMail(ApiMail.builder()
                .to(to.split(SysDefaults.CHAR_SEPARATOR))
                .sentDate(new Date())
                .subject("500 Internal Error")
                .text(errorInfo)
                .build());
    }

    public void sendMail(ApiMail apiMail) {
        ApiMq apiMq = ApiMq.builder()
                .mqType(SysDefaults.MQ_MAIL)
                .mqBody(JsonUtil.toJsonString(apiMail))
                .timestamp(SysDefaults.now())
                .bizKey(KeyTool.newKey("Error"))
                .build();
        this.mqCommonProducer.sendMsg(apiMq);
    }

    @RabbitListener(id = SysDefaults.MQ_MAIL, queues = SysDefaults.MQ_MAIL)
    @RabbitHandler
    public void processMail(@Payload String msg, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        log.info("receive mail:{}", msg);
        try {
            // 解析消息信息
            ApiMq apiMq = JsonUtil.toObj(msg, ApiMq.class);
            ApiMail apiMail = JsonUtil.toObj(apiMq.getMqBody(), ApiMail.class);
            apiMail.setSentDate(new Date());
            // 发送邮件
            this.mailClient.sendMail(apiMail);
            // 确认这条消息
            this.mqApi.basicAck(headers, channel);
        } catch (Exception e) {
            log.error("send mail 代码异常: {}, 原始信息为: {}", e.getMessage(), msg);
        }
    }
}
