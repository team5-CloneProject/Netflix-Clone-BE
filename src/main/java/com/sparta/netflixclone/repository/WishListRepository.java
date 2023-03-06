package com.sparta.netflixclone.repository;

import com.sparta.netflixclone.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList,Long> {
    Optional<WishList> findByMovieIdAndMemberId(Long Id, Long id);
}
