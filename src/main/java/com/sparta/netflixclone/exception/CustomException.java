package com.sparta.netflixclone.exception;


import com.sparta.netflixclone.entity.enumclass.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{

    private final ExceptionEnum exceptionEnum;

}
