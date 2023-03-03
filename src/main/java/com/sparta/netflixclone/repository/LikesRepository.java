package com.sparta.netflixclone.repository;

import com.sparta.netflixclone.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes,Long> {
}
