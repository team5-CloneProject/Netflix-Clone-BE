package com.sparta.netflixclone.service;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.dto.LoginRequestDto;
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

import java.util.Optional;

import static com.sparta.netflixclone.entity.enumclass.ExceptionEnum.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ResponseEntity<ApiResponseDto> signup(SignupRequestDto signupRequestDto) {
        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String nickname = signupRequestDto.getNickname();
        String image = signupRequestDto.getImage();

        Optional<Member> foundUsername = memberRepository.findByEmail(email);
        if (foundUsername.isPresent()) {
            throw new CustomException(DUPLICATE_USER);
        }

        Member member = Member.of(email, password, nickname, image);
        memberRepository.save(member);
        return ResponseEntity.ok()
                .body(ApiResponseDto.of(true, HttpStatus.CREATED));
    }

    public ResponseEntity<ApiResponseDto> login(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        Optional<Member> user = memberRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new CustomException(NOT_EXIST_USER);
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new CustomException(PASSWORD_WRONG);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getEmail()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(ApiResponseDto.of(true, HttpStatus.OK));
    }
}
