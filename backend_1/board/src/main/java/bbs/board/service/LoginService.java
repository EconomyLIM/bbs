package bbs.board.service;

import bbs.board.auth.JwtTokenProvider;
import bbs.board.auth.entity.RefreshToken;
import bbs.board.auth.service.RefreshTokenService;
import bbs.board.domain.Member;
import bbs.board.dto.LoginDTO;
import bbs.board.dto.MemberDTO;
import bbs.board.dto.response.LoginBasicResponse;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    // 토큰 관련 컴포넌트
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void saveMember(MemberDTO memberDTO) {

        // Todo
        // @Valid 사용해서 validation check
        // 이메일 중복 체크

        // 비밀번호 정규표현식 사용해서 잘 만들었는지 체크

        // 비밀번호 암호화
        String encode = passwordEncoder.encode(memberDTO.getPassword());
        memberDTO.setPassword(encode);


        Member member = Member.newMemberWithMemberDTO(memberDTO);
        memberRepository.save(member);
    }

    public LoginBasicResponse login (LoginDTO loginDTO) {

        Member findMember = memberRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SEARCH_MEMBER));

        if (!passwordEncoder.matches(loginDTO.getPassword(), findMember.getPassword())) {
            throw new CustomException(ErrorCode.NO_SEARCH_MEMBER);
        }

        String accessToken = jwtTokenProvider.createToken(findMember.getEmail(), findMember.getNickname());

        return LoginBasicResponse.of(findMember, accessToken, null);
    }
}
