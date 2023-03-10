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


    @Operation(summary = "내 정보 수정 메서드", description = "내 정보 수정 메서드 입니다.")

    @PostMapping("/mypage")
    public ApiResponseDto<SuccessResponse> uploadFile(@RequestParam("images") MultipartFile multipartFile, @RequestParam("nickname") String nickname, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        //return s3Upload.upload(multipartFile);
        return myPageService.profileSave(multipartFile, nickname, userDetails.getMember());
    }


    @Operation(summary = "내 정보 확인 메서드", description = "내 정보 확인 메서드 입니다.")

    @GetMapping("/mypage")
    public ApiResponseDto<MemberResponseDto> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return myPageService.getProfile(userDetails.getMember());
    }
}