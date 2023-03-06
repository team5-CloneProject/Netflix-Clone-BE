package com.sparta.netflixclone.dto;

import com.sparta.netflixclone.entity.enumclass.LikeStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequestDto {
    private LikeStatus status;
}

