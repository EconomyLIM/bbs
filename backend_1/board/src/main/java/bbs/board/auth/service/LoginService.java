package bbs.board.auth.service;

import bbs.board.auth.JwtTokenProvider;
import bbs.board.member.entity.Member;
import bbs.board.auth.dto.LoginDTO;
import bbs.board.member.dto.MemberDTO;
import bbs.board.auth.dto.LoginBasicResponse;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.member.repository.MemberRepository;
import bbs.board.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.jsonwebtoken.*;
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
    private final RedisUtil redisUtil;

    public LoginBasicResponse login (LoginDTO loginDTO) {

        Member findMember = memberRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SEARCH_MEMBER));

        if (!passwordEncoder.matches(loginDTO.getPassword(), findMember.getPassword())) {
            throw new CustomException(ErrorCode.NO_SEARCH_MEMBER);
        }

        String accessToken = jwtTokenProvider.createToken(findMember.getEmail(), findMember.getNickname());
        String refreshToken = jwtTokenProvider.createRefreshToken(findMember.getEmail());

        long refreshExpSec = jwtTokenProvider.getClaims(refreshToken)
                .getExpiration().getTime() - System.currentTimeMillis();
        refreshExpSec = refreshExpSec / 1000; // ms -> s 로 변환

        redisUtil.saveRefreshToken(loginDTO.getEmail(), refreshToken, refreshExpSec);

        return LoginBasicResponse.of(findMember, accessToken, refreshToken);
    }

    // 로그아웃: Access Token 블랙리스트 등록 & Refresh Token 무효화
    public void logout(String accessToken) {
        // accessToken이 유효하다면 남은 만료시간을 계산해서 블랙리스트 등록
        Claims claims = jwtTokenProvider.getClaims(accessToken);
        long now = System.currentTimeMillis();
        long exp = claims.getExpiration().getTime();
        long remainSeconds = (exp - now) / 1000;

        if (remainSeconds > 0) {
            redisUtil.setLogoutToken(accessToken, remainSeconds);
        }

        // Refresh Token 무효화
        String email = jwtTokenProvider.getEmailFromToken(accessToken);
        redisUtil.deleteRefreshToken(email);
    }
}
