package com.airnz.email.emailsample.model.dataObject;

import com.airnz.email.emailsample.enums.EmailRecipientType;
import lombok.Data;

@Data
public class EmailRecipientDO {

    private long id;

    private String mailId;

    private String emailAddress;

    private EmailRecipientType recipientType;

    public EmailRecipientDO(String mailId, String emailAddress, EmailRecipientType recipientType) {
        this.mailId = mailId;
        this.emailAddress = emailAddress;
        this.recipientType = recipientType;
    }
}
