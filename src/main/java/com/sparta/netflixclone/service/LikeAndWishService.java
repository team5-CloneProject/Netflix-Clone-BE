package com.sparta.netflixclone.service;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.common.ResponseUtils;
import com.sparta.netflixclone.common.SuccessResponse;
import com.sparta.netflixclone.dto.LikeRequestDto;
import com.sparta.netflixclone.entity.enumclass.LikeStatus;
import com.sparta.netflixclone.entity.Likes;
import com.sparta.netflixclone.entity.Member;
import com.sparta.netflixclone.exception.CustomException;
import com.sparta.netflixclone.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.sparta.netflixclone.entity.enumclass.ExceptionEnum.*;

@Service
@RequiredArgsConstructor
public class LikeAndWishService {
    private final LikesRepository likesRepository;
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
            return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"LIKE DELETE 변경완료"));
        }

        likesRepository.saveAndFlush(Likes.of(id, member, likeRequestDto.getStatus().toString()));
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"LIKE 저장완료"));
    }






}
