package com.sparta.netflixclone.controller;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.common.SuccessResponse;
import com.sparta.netflixclone.dto.MemberResponseDto;
import com.sparta.netflixclone.entity.Member;
import com.sparta.netflixclone.entity.enumclass.ExceptionEnum;
import com.sparta.netflixclone.exception.CustomException;

import com.sparta.netflixclone.security.UserDetailsImpl;
import com.sparta.netflixclone.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@Tag(name = "내정보 API", description = "내정보 API 입니다.")
@RequestMapping("/api")
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "댓글 추가 메서드", description = "댓글 추가 메서드 입니다.")
    @GetMapping("/abc4")
    public ApiResponseDto<SuccessResponse> abc(){

//        return ResponseUtils.ok(DTO);
        throw new CustomException(ExceptionEnum.JWT_EXPIRED_TOKEN);
//        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"로그인성공"));

    }

    @PostMapping("/mypage")
    public ApiResponseDto<SuccessResponse> uploadFile(@RequestParam("images") MultipartFile multipartFile, @RequestParam("nickname") String nickname, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        //return s3Upload.upload(multipartFile);
        return myPageService.profileSave(multipartFile, nickname, userDetails.getMember());
    }


    @GetMapping("/mypage")
    public ApiResponseDto<MemberResponseDto> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return myPageService.getProfile(userDetails.getMember());
    }
}