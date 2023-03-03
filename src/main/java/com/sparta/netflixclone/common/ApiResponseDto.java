package com.sparta.netflixclone.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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



}
