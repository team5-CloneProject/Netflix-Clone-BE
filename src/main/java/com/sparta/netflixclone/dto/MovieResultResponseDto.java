package com.sparta.netflixclone.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MovieResultResponseDto {
    private Long id;
    private String overview;
    private LocalDate release_date;
    private String title;
    private float vote_average;

    private String popularity;
    private String poster_path;
    private int vote_count;
    private String original_language;

    public void setPoster_path(String poster_path) {
        this.poster_path = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/"+poster_path;
    }
}
