package com.sparta.netflixclone.controller;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.common.ResponseUtils;
import com.sparta.netflixclone.common.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "좋아요, 찜하기", description = "좋아요, 찜하기 API 입니다.")
@RequestMapping("/api")
public class LikeAndWishController {

    @Operation(summary = "댓글 추가 메서드", description = "댓글 추가 메서드 입니다.")
    @GetMapping("/abc1")
    public ApiResponseDto<SuccessResponse> abc(){

//        return ResponseUtils.ok(DTO);
//        throw new CustomException(ExceptionEnum.JWT_EXPIRED_TOKEN);
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"로그인성공"));

    }
}
