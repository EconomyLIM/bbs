package bbs.board.service;

import bbs.board.domain.Member;
import bbs.board.dto.LoginDTO;
import bbs.board.dto.MemberDTO;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    @Transactional
    public void saveMember(MemberDTO memberDTO) {

        // @Valid 사용해서 validation check
        // 이메일 중복 체크

        // 비밀번호 정규표현식 사용해서 잘 만들었는지 체크


        Member member = Member.newMemberWithMemberDTO(memberDTO);
        memberRepository.save(member);
    }

    public Member login (LoginDTO loginDTO) {
        Member loginedMember = memberRepository.login(loginDTO);

        // 로그인 멤버가 있는지 체크
        if (loginedMember == null) {
            throw new CustomException(ErrorCode.INVALID_AUTHENTICATION);
        }

        return loginedMember;
    }
}
