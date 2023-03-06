package com.sparta.netflixclone.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(nullable = false)
    private Long movieId;

    @ManyToOne
    Member member;

//    @Column(nullable = false)
//    private boolean status;

    @Builder
    public WishList(Long movieId, Member member) {
        this.movieId = movieId;
        this.member = member;
    }

    public static WishList of(Long movieId, Member member) {
        return WishList.builder()
                .movieId(movieId)
                .member(member)
                .build();
    }
}
