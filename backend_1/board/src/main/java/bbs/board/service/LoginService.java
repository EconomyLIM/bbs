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
        Member member = Member.newMemberWithMemberDTO(memberDTO);
        memberRepository.save(member);
    }

    public Member login (LoginDTO loginDTO) {
        Member loginedMember = memberRepository.login(loginDTO);

        if (loginedMember == null) {
            throw new CustomException(ErrorCode.INVALID_AUTHENTICATION);
        }

        return loginedMember;
    }
}
