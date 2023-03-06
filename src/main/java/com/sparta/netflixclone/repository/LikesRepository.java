package com.sparta.netflixclone.repository;

import com.sparta.netflixclone.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes,Long> {
    Optional<Likes> findByMovieIdAndMemberId(Long Id, Long id);
}
