package com.airnz.email.emailsample.model.command;

import com.airnz.email.emailsample.model.req.EmailRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestCommand {

    private String emailId;

    private String fomrAddress;

    private EmailRequest emailRequest;

    private EmailRequestCommand(Builder builder) {
        setEmailId(builder.emailId);
        setFomrAddress(builder.fomrAddress);
        setEmailRequest(builder.emailRequest);
    }


    public static final class Builder {
        private String emailId;
        private String fomrAddress;
        private EmailRequest emailRequest;

        public Builder() {
        }

        public Builder withEmailId(String val) {
            emailId = val;
            return this;
        }

        public Builder withFomrAddress(String val) {
            fomrAddress = val;
            return this;
        }

        public Builder withEmailRequest(EmailRequest val) {
            emailRequest = val;
            return this;
        }

        public EmailRequestCommand build() {
            return new EmailRequestCommand(this);
        }
    }
}
