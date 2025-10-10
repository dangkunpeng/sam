package com.sam.sam_msg.mail;

import com.sam.sam_msg.mq.MqMailApi;
import com.sam.sap_commons.exchange.ApiMsg;
import com.sam.sap_commons.utils.FmtUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/mail")
public class MailController {

    @Resource
    private MqMailApi mqMailApi;

    @RequestMapping("/error/{errorInfo}")
    public ApiMsg<String> sendError(@PathVariable String errorInfo) {
        this.mqMailApi.sendMail(errorInfo);
        return ApiMsg.success(FmtUtils.fmtMsg("send success {}", errorInfo));
    }
}
