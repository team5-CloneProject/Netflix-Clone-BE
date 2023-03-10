package com.sparta.netflixclone.common;

public class ResponseUtils {

    public static <T> ApiResponseDto<T> ok(T data) {
        return ApiResponseDto.<T>builder()
                .success(true)
                .response(data)
                .build();
    }

    public static <T> ApiResponseDto<T> error(T data) {
        return ApiResponseDto.<T>builder()
                .success(false)
                .response(data)
                .build();
    }
}
