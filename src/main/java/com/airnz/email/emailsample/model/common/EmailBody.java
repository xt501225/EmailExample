package com.airnz.email.emailsample.model.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailBody {

    @JsonProperty("subject")
    @NotNull(message = "subject must not be nul")
    private String subject;

    @JsonProperty("contents")
    @Valid
    private List<EmailContent> contents;
}
