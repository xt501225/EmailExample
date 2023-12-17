package com.airnz.email.emailsample.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.airnz.email.emailsample.enums.EmailType;
import com.airnz.email.emailsample.model.common.EmailBody;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmailResponse {
    private String id;

    private String from;

    private List<String> to;

    private List<String> cc;

    private List<String> bcc;

    private EmailBody Body;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private EmailType emailType;

}
