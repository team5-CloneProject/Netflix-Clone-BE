package com.sparta.netflixclone.service;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.common.ResponseUtils;
import com.sparta.netflixclone.dto.MovieResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MovieService {
    @Value("${tmdb.key}")
    private String key;
    public ApiResponseDto<MovieResponseDto> movieLatestView(int page) {

        RestTemplate restTemplate = new RestTemplate();
        MovieResponseDto movieResponse = restTemplate.getForObject("https://api.themoviedb.org/3/movie/popular?api_key="+key+"&language=ko-KR&page="+page, MovieResponseDto.class);

        return ResponseUtils.ok(movieResponse);

    }
}
