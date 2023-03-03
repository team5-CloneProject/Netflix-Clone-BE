package com.sparta.netflixclone.controller;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.common.ResponseUtils;
import com.sparta.netflixclone.common.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@Tag(name = "멤버 API", description = "멤버 API 입니다.")
@RequestMapping("/api")
public class MemberController {

    @Operation(summary = "댓글 추가 메서드", description = "댓글 추가 메서드 입니다.")
    @GetMapping("/abc2")
    public ApiResponseDto<SuccessResponse> abc(){

//        return ResponseUtils.ok(DTO);
//        throw new CustomException(ExceptionEnum.JWT_EXPIRED_TOKEN);
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"로그인성공"));

    }
}