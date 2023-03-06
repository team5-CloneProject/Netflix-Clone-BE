package com.sparta.netflixclone.service;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.common.ResponseUtils;
import com.sparta.netflixclone.dto.MovieResponseDto;
import com.sparta.netflixclone.entity.Likes;
import com.sparta.netflixclone.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    @Value("${tmdb.key}")
    private String key;

    public ApiResponseDto<MovieResponseDto> moviePopular(int page) {
        RestTemplate restTemplate = new RestTemplate();
        MovieResponseDto movieResponse = restTemplate.getForObject("https://api.themoviedb.org/3/movie/popular?api_key=" + key + "&language=ko-KR&page=" + page + "&region=KR", MovieResponseDto.class);
        return ResponseUtils.ok(movieResponse);
    }

    public ApiResponseDto<MovieResponseDto> movieTopRated(int page) {
        RestTemplate restTemplate = new RestTemplate();
        MovieResponseDto movieResponse = restTemplate.getForObject("https://api.themoviedb.org/3/movie/top_rated?api_key=" + key + "&language=ko-KR&page=" + page + "&region=KR", MovieResponseDto.class);
        return ResponseUtils.ok(movieResponse);
    }

    public ApiResponseDto<MovieResponseDto> movieNowPlaying(int page) {
        RestTemplate restTemplate = new RestTemplate();
        MovieResponseDto movieResponse = restTemplate.getForObject("https://api.themoviedb.org/3/movie/now_playing?api_key=" + key + "&language=ko-KR&page=" + page + "&region=KR", MovieResponseDto.class);
        return ResponseUtils.ok(movieResponse);
    }

    public ApiResponseDto<MovieResponseDto> movieSearch(int page,String query) {
        RestTemplate restTemplate = new RestTemplate();
        MovieResponseDto movieResponse = restTemplate.getForObject("https://api.themoviedb.org/3/search/multi?api_key="+key+"&language=ko-KR&query="+query+"&page="+page+"&include_adult=false&region=KR", MovieResponseDto.class);
        return ResponseUtils.ok(movieResponse);
    }
    public ApiResponseDto<MovieResponseDto> movieTvPopular(int page) {
        RestTemplate restTemplate = new RestTemplate();
        MovieResponseDto movieResponse = restTemplate.getForObject("https://api.themoviedb.org/3/tv/popular?api_key="+key+"&language=en-US&page="+page, MovieResponseDto.class);
        return ResponseUtils.ok(movieResponse);
    }


}
