package com.airnz.email.emailsample.model.dataObject;

import com.airnz.email.emailsample.enums.EmailContentType;
import lombok.Data;

@Data
public class EmailContentDO {

    private long id;

    private String mailId;

    private EmailContentType type;

    private String content;

    public EmailContentDO(String mailId, EmailContentType type, String content) {
        this.mailId = mailId;
        this.type = type;
        this.content = content;
    }

}
