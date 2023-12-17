package com.airnz.email.emailsample.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.airnz.email.emailsample.exception.UnauthorizedException;
import com.airnz.email.emailsample.model.dto.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseDto> bindExceptionHandler(BindException e) {
        String fieldError = e.getBindingResult().getFieldError().getDefaultMessage();
        LOG.error(e.getMessage(), e);
        ResponseDto dto = ResponseDto.buildFailure(HttpStatusCode.valueOf(400), fieldError);
        return ResponseEntity.badRequest().body(dto);
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseDto> bindExceptionHandler(UnauthorizedException e) {
        LOG.error(e.getMessage(), e);
        ResponseDto dto = ResponseDto.buildFailure(HttpStatusCode.valueOf(401), e.getMessage());
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(dto);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResponseDto> handle(Exception e) {
        LOG.error(e.getMessage(), e);
        ResponseDto dto = ResponseDto.buildFailure(HttpStatusCode.valueOf(503), e.getMessage());
        return ResponseEntity.internalServerError().body(dto);
    }
}
