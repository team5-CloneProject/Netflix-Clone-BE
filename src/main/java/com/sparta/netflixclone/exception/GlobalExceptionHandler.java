package com.sparta.netflixclone.exception;



import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.common.ErrorResponse;
import com.sparta.netflixclone.common.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
//    protected ResponseEntity<ApiResponseDto<ErrorResponse>> customException(CustomException e){
        protected ResponseEntity<ApiResponseDto<ErrorResponse>> customException(CustomException e){

//        return ErrorResponse.toResponseEntity(e.getExceptionEnum());
        return ResponseEntity.status(e.getExceptionEnum().getCode())
                .body(ResponseUtils.error(
                        ErrorResponse.of(e.getExceptionEnum().getCode(),e.getExceptionEnum().getMsg())
                ));

    }

}
