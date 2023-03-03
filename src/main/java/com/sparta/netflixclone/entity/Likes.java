package com.sparta.netflixclone.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private Long movieId;

    @ManyToOne
    Member member;

    @Column(nullable = false)
    private String status;

    @Builder
    public Likes(Long movieId, Member member, String status) {
        this.movieId = movieId;
        this.member = member;
        this.status = status;
    }
}
