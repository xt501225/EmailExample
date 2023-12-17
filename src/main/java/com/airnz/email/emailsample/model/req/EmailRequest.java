package com.airnz.email.emailsample.model.req;

import com.airnz.email.emailsample.model.common.EmailBody;
import com.airnz.email.emailsample.model.common.EmailRecipient;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailRequest {

    @JsonProperty("to")
    private EmailRecipient emailRecipient;

    @JsonProperty("body")
    @Valid
    private EmailBody emailBody;
}
