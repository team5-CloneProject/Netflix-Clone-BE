package com.sparta.netflixclone.controller;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.common.ResponseUtils;
import com.sparta.netflixclone.common.SuccessResponse;
import com.sparta.netflixclone.dto.LoginRequestDto;
import com.sparta.netflixclone.dto.MovieResponseDto;
import com.sparta.netflixclone.dto.SignupRequestDto;
import com.sparta.netflixclone.entity.enumclass.ExceptionEnum;
import com.sparta.netflixclone.exception.CustomException;
import com.sparta.netflixclone.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@Tag(name = "멤버 API", description = "멤버 API 입니다.")
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;


    @Operation(summary = "회원가입 메서드", description = "회원가입 메서드 입니다.")
    @PostMapping("/signup")
    public ApiResponseDto<SuccessResponse> signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ExceptionEnum.INVALID_EMAIL_REG);
        }
        return memberService.signup(signupRequestDto);
    }

    @Operation(summary = "로그인 메서드", description = "로그인 메서드 입니다.")
    @PostMapping("/login")
    public ApiResponseDto<SuccessResponse> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.login(loginRequestDto, response);
    }

    @Operation(summary = "가입자 확인 메서드", description = "가입자 확인 메서드 입니다.")
    @GetMapping
    public ApiResponseDto<SuccessResponse> getMemberByEmail(@RequestParam String email) {
        // email에 해당하는 멤버 정보를 조회하는 로직 구현
        return memberService.checkEmail(email);
    }

}