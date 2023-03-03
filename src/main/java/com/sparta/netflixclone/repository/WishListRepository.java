package com.sparta.netflixclone.repository;

import com.sparta.netflixclone.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList,Long> {
}
