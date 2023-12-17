package com.airnz.email.emailsample.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class SingleResponseDto<T> extends  ResponseDto {
    private T data;

    public static <T> SingleResponseDto<T> of(T data) {
        SingleResponseDto<T> response = new SingleResponseDto<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
}
