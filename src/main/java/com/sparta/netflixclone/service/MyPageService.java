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

        if(multipartFile != null && findMember.getImage() != null && !findMember.getImage().equals("https://netflix-clone-image.s3.ap-northeast-2.amazonaws.com/usericon.png")){
            s3Upload.deleteS3File(findMember.getImage());
        }

        if(multipartFile.isEmpty()){
            findMember.update(findMember.getImage(), nickname);
        } else if(nickname.isEmpty()) {
            String url = s3Upload.upload(multipartFile);
            findMember.update(url, findMember.getNickname());
        } else {
            String url = s3Upload.upload(multipartFile);
            findMember.update(url, nickname);
        }

        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "프로필 수정 완료"));
    }
}