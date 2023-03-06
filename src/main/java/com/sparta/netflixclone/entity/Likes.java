package com.sparta.netflixclone.entity;

import com.sparta.netflixclone.dto.LikeRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
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

    public static Likes of(Long movieId, Member member, String status) {
        return Likes.builder()
                .movieId(movieId)
                .member(member)
                .status(status)
                .build();
    }

    public void update(String statusUpdate) {
        this.status = statusUpdate;
    }
}
