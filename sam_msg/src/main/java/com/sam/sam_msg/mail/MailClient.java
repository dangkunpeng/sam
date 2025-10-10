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
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getSubject());
            // 发件人的邮箱
            mimeMessageHelper.setFrom(new InternetAddress("IAP.Robot@aspiro.co", "IAP RPA"));
            // 要发给的邮箱
            mimeMessageHelper.setTo(mail.getTo());
            //邮件内容(html渲染 所以要填true)
            mimeMessageHelper.setText(mail.getText(), true);
            // 发送日期
            mimeMessageHelper.setSentDate(new Date());
            // 发送邮件
            this.javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("sendMail Exception = {}, mail detail ={}", e.getMessage(), JsonUtil.toJsonString(mail));
            throw new Exception("邮件发送失败" + e.getMessage());
        }
    }
}
