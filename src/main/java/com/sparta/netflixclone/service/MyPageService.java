package com.sparta.netflixclone.service;

import com.sparta.netflixclone.common.ApiResponseDto;
import com.sparta.netflixclone.common.ResponseUtils;
import com.sparta.netflixclone.dto.MemberResposeDto;
import com.sparta.netflixclone.entity.Member;
import com.sparta.netflixclone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
    public ApiResponseDto<MemberResposeDto> getProfile(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        MemberResposeDto memberResposeDto = new MemberResposeDto(findMember);
        return ResponseUtils.ok(memberResposeDto);
    }


    // 마이페이지 수정
    @Transactional
    public ApiResponseDto<Member> profileSave(MultipartFile multipartFile, String nickname, Member member) throws IOException {

        Member findMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        if (multipartFile != null) {
            String url = s3Upload.upload(multipartFile);
            findMember.update(url, nickname);
        }
        return ResponseUtils.ok(findMember);
    }
}