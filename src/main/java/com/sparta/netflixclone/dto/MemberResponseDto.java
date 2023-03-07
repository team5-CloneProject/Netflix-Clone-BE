package com.sparta.netflixclone.dto;

import com.sparta.netflixclone.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private String nickname;
    private String image;

    public MemberResponseDto(Member member){
        this.nickname = member.getNickname();
        this.image = member.getImage();
    }
}
