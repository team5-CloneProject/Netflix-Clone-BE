package com.sparta.netflixclone.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter


public class ApiResponseDto<T> {



    private boolean success;
    private T response;




    @Builder
    private ApiResponseDto(boolean success, T response) {
        this.success = success;
        this.response = response;

    }
    public static ApiResponseDto of(boolean success, HttpStatus response) {
        return ApiResponseDto.builder()
                .success(success)
                .response(response)
                .build();
    }



}
