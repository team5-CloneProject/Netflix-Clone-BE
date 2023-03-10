package com.sparta.netflixclone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequestDto {

    @NotNull(message = "아이디는 필수 값입니다.")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9\\-]+\\.)+([a-zA-Z]{2,5})$", message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotNull(message = "비밀번호는 필수 값입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,32}$", message = "비밀번호는 대소문자, 숫자, 특수문자를 포함하여 8-32자 이내여야 합니다.")
    private String password;

    @NotNull(message = "닉네임은 필수 값입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2-10자 이내 입니다.")
    private String nickname;
    private String image;
}
