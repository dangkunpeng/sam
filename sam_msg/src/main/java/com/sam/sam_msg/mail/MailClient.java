package com.sam.sam_msg.mail;

import com.sam.sap_commons.exchange.ApiMail;
import com.sam.sap_commons.utils.JsonUtil;
import jakarta.annotation.Resource;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MailClient {

    @Resource
    private JavaMailSender javaMailSender;

    public void sendMail(ApiMail mail) throws Exception {
        log.info("邮件发送{}", JsonUtil.toJsonString(mail));
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(mail.getSubject());
            // 发件人的邮箱
            helper.setFrom(new InternetAddress("IAP.Robot@aspiro.co", "IAP RPA"));
            // 要发给的邮箱
            helper.setTo(mail.getTo());
            //邮件内容(html渲染 所以要填true)
            helper.setText(mail.getText(), true);
            // 发送日期
            helper.setSentDate(new Date());
            // 发送邮件
            this.javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("sendMail Exception = {}", e);
            throw new Exception("邮件发送失败" + e.getMessage());
        }
    }
}
