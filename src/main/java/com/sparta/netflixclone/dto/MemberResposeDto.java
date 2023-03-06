package com.sparta.netflixclone.dto;

import com.sparta.netflixclone.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResposeDto {
    private String nickname;
    private String image;

    public MemberResposeDto(Member member){
        this.nickname = member.getNickname();
        this.image = member.getImage();
    }
}
