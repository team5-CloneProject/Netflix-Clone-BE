package com.sparta.netflixclone.exception;



import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.common.ErrorResponse;
import com.sparta.netflixclone.common.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.net.UnknownHostException;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
        protected ResponseEntity<ApiResponseDto<ErrorResponse>> customException(CustomException e){

//        return ErrorResponse.toResponseEntity(e.getExceptionEnum());
        return ResponseEntity.status(e.getExceptionEnum().getCode())
                .body(ResponseUtils.error(
                        ErrorResponse.of(e.getExceptionEnum().getCode(),e.getExceptionEnum().getMsg())
                ));

    }

    @ExceptionHandler(UnknownHostException.class)
    protected ResponseEntity<ApiResponseDto<ErrorResponse>> customException(UnknownHostException e){

//        return ErrorResponse.toResponseEntity(e.getExceptionEnum());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(ResponseUtils.error(
                        ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage())
                ));

    }

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<ApiResponseDto<ErrorResponse>> customException(HttpClientErrorException e){

//        return ErrorResponse.toResponseEntity(e.getExceptionEnum());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(ResponseUtils.error(
                        ErrorResponse.of(HttpStatus.NOT_FOUND.value(),e.getMessage())
                ));

    }
}
