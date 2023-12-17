package com.airnz.email.emailsample.model.dto;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultiResponseDto<T> extends ResponseDto {
    private Collection<T> data;

    public static <T> MultiResponseDto<T> of(Collection<T> data) {
        MultiResponseDto<T> response = new MultiResponseDto<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
}
