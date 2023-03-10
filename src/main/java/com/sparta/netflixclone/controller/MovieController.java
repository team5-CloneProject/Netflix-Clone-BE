package com.sparta.netflixclone.controller;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.dto.MovieResponseDto;
import com.sparta.netflixclone.security.UserDetailsImpl;
import com.sparta.netflixclone.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Tag(name = "영화조회 API", description = "영화조회 API 입니다.")
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;

    @Operation(summary = "영화 인기순 메서드", description = "영화 인기순 메서드 입니다.")
    @GetMapping("/movie/popular")
    public ApiResponseDto<MovieResponseDto> moviePopular(@RequestParam int page, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return movieService.moviePopular(page,userDetails.getUser());
    }

    @Operation(summary = "영화 평점순 메서드", description = "영화 평점순 메서드 입니다.")
    @GetMapping("/movie/toprated")
    public ApiResponseDto<MovieResponseDto> movieTopRated(@RequestParam int page,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return movieService.movieTopRated(page,userDetails.getUser());
    }

    @Operation(summary = "영화 현재 상영 조회 메서드", description = "영화 현재 상영중 조회 메서드 입니다.")
    @GetMapping("/movie/nowplaying")
    public ApiResponseDto<MovieResponseDto> movieNowPlaying(@RequestParam int page,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return movieService.movieNowPlaying(page,userDetails.getUser());
    }
    @Operation(summary = "TV 인기순 메서드", description = "TV 인기순 메서드 입니다.")
    @GetMapping("/movie/tvpopular")
    public ApiResponseDto<MovieResponseDto> movieTvPopular(@RequestParam int page,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return movieService.movieTvPopular(page,userDetails.getUser());
    }


    @Operation(summary = "영화 검색 메서드", description = "영화 검색 메서드 입니다.")
    @GetMapping("/movie/search")
    public ApiResponseDto<MovieResponseDto> movieNowPlaying(@RequestParam int page,@RequestParam String query,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return movieService.movieSearch(page,query,userDetails.getUser());
    }
}
