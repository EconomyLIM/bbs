package bbs.board.service;

import bbs.board.domain.Member;
import bbs.board.dto.LoginDTO;
import bbs.board.dto.response.MemberLoginResponse;
import bbs.board.dto.response.MemberSaveResponse;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.repository.MemberRepository;
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

    public MemberSaveResponse save(final Member member) {
        final Long saveId = memberRepository.save(member);
        return MemberSaveResponse.of(saveId);
    }

    public MemberLoginResponse login(final LoginDTO loginDTO){
        final Member loginedMember = memberRepository.login(loginDTO);
        if (loginedMember == null) {
            throw new CustomException(ErrorCode.NO_SEARCH_MEMBER);
        }

        return MemberLoginResponse.of(loginedMember);
    }

}
