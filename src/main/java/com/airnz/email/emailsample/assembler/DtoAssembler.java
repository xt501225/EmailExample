package com.airnz.email.emailsample.assembler;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.airnz.email.emailsample.enums.EmailRecipientType;
import com.airnz.email.emailsample.model.common.EmailBody;
import com.airnz.email.emailsample.model.common.EmailContent;
import com.airnz.email.emailsample.model.dataObject.EmailDO;
import com.airnz.email.emailsample.model.dto.EmailResponse;

@Component
public class DtoAssembler {

    public EmailResponse dataObjectToDto(EmailDO emailDO) {
        EmailResponse response = new EmailResponse();
        response.setFrom(emailDO.getFromAddress());
        response.setTo(getRecipientAddresses(emailDO, EmailRecipientType.TO));
        response.setCc(getRecipientAddresses(emailDO, EmailRecipientType.CC));
        response.setBcc(getRecipientAddresses(emailDO, EmailRecipientType.BCC));
        EmailBody body = new EmailBody();
        body.setSubject(emailDO.getSubject());
        body.setContents(emailDO.getEmailContents().stream().map(t -> new EmailContent(t.getType().name(),
                t.getContent())).collect(Collectors.toList()));
        response.setBody(body);
        response.setDate(new Timestamp(emailDO.getCreatedAt()).toLocalDateTime());
        return response;
    }

    private List<String> getRecipientAddresses(EmailDO emailDO, EmailRecipientType type) {
        return emailDO.getRecipientDOs().stream().filter(emailRecipientDO -> emailRecipientDO.getRecipientType().equals(type))
                .map(t -> t.getEmailAddress()).collect(Collectors.toList());
    }
}
