package com.airnz.email.emailsample.model.common;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailRecipient {

    @JsonProperty("recipient_to")
    private List<EmailAddress> recipientTo = new ArrayList<>();

    @JsonProperty("recipient_cc")
    private List<EmailAddress> recipientCc = new ArrayList<>();

    @JsonProperty("recipient_bcc")
    private List<EmailAddress> recipientBcc = new ArrayList<>();
}
