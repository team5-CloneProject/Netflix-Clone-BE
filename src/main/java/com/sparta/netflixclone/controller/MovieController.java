package com.sparta.netflixclone.controller;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.dto.MovieResponseDto;
import com.sparta.netflixclone.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public ApiResponseDto<MovieResponseDto> moviePopular(@RequestParam int page){

        return  movieService.movieLatestView(page);

    }
}