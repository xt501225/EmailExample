package com.airnz.email.emailsample.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailContent {

    @JsonProperty("content_type")
    @NotNull(message = "content type should not be null")
    private String type;

    @JsonProperty("content_details")
    @NotNull(message = "content details should not be null")
    private String detail;

    public EmailContent(String type, String detail) {
        this.type = type;
        this.detail = detail;
    }
}
