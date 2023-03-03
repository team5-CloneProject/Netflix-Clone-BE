package com.sparta.netflixclone.entity;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;

    private String image;


    @Builder
    public Member(String name, String password, String email,String image) {
        this.nickname = name;
        this.password = password;
        this.email = email;
        this.image = image;
    }

    public static Member of(String email, String password, String nickname, String image) {
        return Member.builder()
                .email(email)
                .password(password)
                .name(nickname)
                .image(image)
                .build();
    }


}