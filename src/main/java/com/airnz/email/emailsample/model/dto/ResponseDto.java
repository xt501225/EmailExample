package com.airnz.email.emailsample.model.dto;

import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private boolean success;
    private HttpStatusCode errCode;
    private String errMessage;

    public static ResponseDto buildSuccess() {
        ResponseDto response = new ResponseDto();
        response.setSuccess(true);
        return response;
    }

    public static ResponseDto buildFailure(HttpStatusCode errCode, String errMessage) {
        ResponseDto response = new ResponseDto();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }
}
