package com.sparta.netflixclone.service;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.common.ResponseUtils;
import com.sparta.netflixclone.common.SuccessResponse;
import com.sparta.netflixclone.dto.LikeRequestDto;
import com.sparta.netflixclone.entity.Likes;
import com.sparta.netflixclone.entity.Member;
import com.sparta.netflixclone.entity.WishList;
import com.sparta.netflixclone.entity.enumclass.LikeStatus;
import com.sparta.netflixclone.exception.CustomException;
import com.sparta.netflixclone.repository.LikesRepository;
import com.sparta.netflixclone.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.sparta.netflixclone.entity.enumclass.ExceptionEnum.WRONG_VALUE;

@Service
@RequiredArgsConstructor
public class LikeAndWishService {
    private final LikesRepository likesRepository;
    private final WishListRepository wishListRepository;
    @Transactional
    public ApiResponseDto<SuccessResponse> createLike(Long id, Member member, LikeRequestDto likeRequestDto) {
        if (!likeRequestDto.getStatus().equals(LikeStatus.LIKE)) {
            throw new CustomException(WRONG_VALUE);
        }

        Optional<Likes> movieLike = likesRepository.findByMovieIdAndMemberId(id, member.getId());

        if (movieLike.isPresent()) {
            if(movieLike.get().getStatus().equals("DISLIKE")) {
                movieLike.get().update(likeRequestDto.getStatus().toString());
                return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"LIKE로 변경완료"));
            }
            likesRepository.delete(movieLike.get());
            return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"LIKE DELETE 완료"));
        }

        likesRepository.saveAndFlush(Likes.of(id, member, likeRequestDto.getStatus().toString()));
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"LIKE 저장완료"));
    }



    @Transactional
    public ApiResponseDto<SuccessResponse> createDisLike(Long id, Member member, LikeRequestDto likeRequestDto) {
        if (!likeRequestDto.getStatus().equals(LikeStatus.DISLIKE)) {
            throw new CustomException(WRONG_VALUE);
        }

        Optional<Likes> movieLike = likesRepository.findByMovieIdAndMemberId(id, member.getId());

        if (movieLike.isPresent()) {
            if(movieLike.get().getStatus().equals("LIKE")) {
                movieLike.get().update(likeRequestDto.getStatus().toString());
                return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"DISLIKE로 변경 완료"));
            }
            likesRepository.delete(movieLike.get());
            return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"DISLIKE DELETE 완료"));
        }

        likesRepository.saveAndFlush(Likes.of(id, member, likeRequestDto.getStatus().toString()));
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"DISLIKE 저장완료"));
    }


    public ApiResponseDto<SuccessResponse> postLike(Long id, Member member) {
        Optional<WishList> postLike = wishListRepository.findByMovieIdAndMemberId(id, member.getId());

        if (postLike.isPresent()) {
            wishListRepository.delete(postLike.get());
            return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"찜하기 취소 완료"));
        }

        wishListRepository.saveAndFlush(WishList.of(id, member));
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"찜하기 등록 완료"));
    }
}
