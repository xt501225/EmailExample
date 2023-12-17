package com.airnz.email.emailsample.model.dataObject;

import java.util.ArrayList;
import java.util.List;

import com.airnz.email.emailsample.enums.EmailRecipientType;
import com.airnz.email.emailsample.enums.EmailType;
import lombok.Data;

@Data
public class EmailDO {

    //unique mailId
    private String id;

    private long createdAt;

    private String emailAddress;

    private EmailType emailType;

    private String fromAddress;

    private String subject;

    private List<EmailContentDO> emailContents = new ArrayList<>();

    private List<EmailRecipientDO> recipientDOs = new ArrayList<>();

    private EmailRecipientType recipientType;

    private String originalId;
}
