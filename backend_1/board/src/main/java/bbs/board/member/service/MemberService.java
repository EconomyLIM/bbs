package bbs.board.member.service;

import bbs.board.member.entity.Member;
import bbs.board.auth.dto.LoginDTO;
import bbs.board.member.dto.MemberLoginResponse;
import bbs.board.member.dto.MemberSaveBasicResponse;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberSaveBasicResponse save(final Member member) {
        final Long saveId = memberRepository.save(member);
        return MemberSaveBasicResponse.of(saveId);
    }

    public MemberLoginResponse login(final LoginDTO loginDTO){
        final Member loginedMember = memberRepository.login(loginDTO);
        if (loginedMember == null) {
            throw new CustomException(ErrorCode.NO_SEARCH_MEMBER);
        }

        return MemberLoginResponse.of(loginedMember);
    }

}
