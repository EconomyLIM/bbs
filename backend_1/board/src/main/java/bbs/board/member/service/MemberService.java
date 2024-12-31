package bbs.board.member.service;

import bbs.board.common.dto.BasicResponse;
import bbs.board.member.dto.MemberDTO;
import bbs.board.member.dto.MemberUpdateRequest;
import bbs.board.member.entity.Member;
import bbs.board.auth.dto.LoginDTO;
import bbs.board.member.dto.MemberLoginResponse;
import bbs.board.member.dto.MemberSaveBasicResponse;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public BasicResponse saveMember(MemberDTO memberDTO) {

        // 이메일 중복 체크
        memberRepository.findByEmail(memberDTO.getEmail()).ifPresent(
                value -> {throw new CustomException(ErrorCode.ALREADY_EMAIL_EXIST);}
        );

        // Todo
        // 비밀번호 정규표현식 사용해서 잘 만들었는지 체크

        // 비밀번호 암호화
        String encode = passwordEncoder.encode(memberDTO.getPassword());
        memberDTO.setPassword(encode);

        Member member = Member.newMemberWithMemberDTO(memberDTO);
        memberRepository.save(member);

        return BasicResponse.of();
    }

    public BasicResponse update(final MemberUpdateRequest request){

        Member findMember = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_USER_FOUND));

        if (!passwordEncoder.matches(request.getCurrentPassword(), findMember.getPassword())) {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }

        String encodedPassword = passwordEncoder.encode(request.getChangePassword());
        request.setChangePassword(encodedPassword);

        findMember.update(request);
        return BasicResponse.of();
    }

    public Member findMember(String email){
        Member findMember = memberRepository.findByEmail(email).
                orElseThrow(() -> new CustomException(ErrorCode.NOT_USER_FOUND));

        return findMember;
    }

}
