package com.sparta.netflixclone.service;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.common.ResponseUtils;
import com.sparta.netflixclone.common.SuccessResponse;
import com.sparta.netflixclone.dto.LoginRequestDto;
import com.sparta.netflixclone.dto.MovieResponseDto;
import com.sparta.netflixclone.dto.SignupRequestDto;
import com.sparta.netflixclone.entity.Member;
import com.sparta.netflixclone.exception.CustomException;
import com.sparta.netflixclone.jwt.JwtUtil;
import com.sparta.netflixclone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.sparta.netflixclone.entity.enumclass.ExceptionEnum.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final S3Upload s3Upload;

    public ApiResponseDto<SuccessResponse> signup(SignupRequestDto signupRequestDto) {
        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String nickname = signupRequestDto.getNickname();
        String image = "https://netflix-clone-image.s3.ap-northeast-2.amazonaws.com/usericon.png";

        Optional<Member> foundUsername = memberRepository.findByEmail(email);
        if (foundUsername.isPresent()) {
            throw new CustomException(DUPLICATE_USER);
        }

        Member member = Member.of(email, password, nickname, image);
        memberRepository.save(member);
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.CREATED, "회원가입 완료"));
    }

    public ApiResponseDto<SuccessResponse> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        Optional<Member> user = memberRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new CustomException(NOT_EXIST_USER);
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new CustomException(PASSWORD_WRONG);
        }

        //HttpHeaders headers = new HttpHeaders();
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getEmail()));

        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "로그인 완료"));
    }

    public ApiResponseDto<SuccessResponse> checkEmail(String email) {
        Optional<Member> foundUsername = memberRepository.findByEmail(email);
        if (foundUsername.isPresent()) {
            throw new CustomException(DUPLICATE_USER);
        }
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "사용가능한 이메일입니다."));
    }
}
