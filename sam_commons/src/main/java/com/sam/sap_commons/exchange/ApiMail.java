package com.sam.sap_commons.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * copyed from org.springframework.mail.SimpleMailMessage
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiMail {

    private String from;

    private String replyTo;

    private String[] to;

    private String[] cc;

    private String[] bcc;

    private Date sentDate;

    private String subject;

    private String text;
}
