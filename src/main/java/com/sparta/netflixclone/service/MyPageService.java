package com.sparta.netflixclone.service;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.common.ResponseUtils;
import com.sparta.netflixclone.common.SuccessResponse;
import com.sparta.netflixclone.dto.MemberResponseDto;
import com.sparta.netflixclone.entity.Member;
import com.sparta.netflixclone.entity.enumclass.ExceptionEnum;
import com.sparta.netflixclone.exception.CustomException;
import com.sparta.netflixclone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final S3Upload s3Upload;
    private final MemberRepository memberRepository;

    //마이페이지 조회
    @Transactional
    public ApiResponseDto<MemberResponseDto> getProfile(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(
                () -> new CustomException(ExceptionEnum.NOT_EXIST_USER)
        );
        MemberResponseDto memberResponseDto = new MemberResponseDto(findMember);
        return ResponseUtils.ok(memberResponseDto);
    }


    // 마이페이지 수정
    @Transactional
    public ApiResponseDto<SuccessResponse> profileSave(MultipartFile multipartFile, String nickname, Member member) throws IOException {

        Member findMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(
                () -> new CustomException(ExceptionEnum.NOT_EXIST_USER)
        );

        if (multipartFile != null) {
            String url = s3Upload.upload(multipartFile);
            findMember.update(url, nickname);
        }
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "프로필 수정 완료"));
    }
}