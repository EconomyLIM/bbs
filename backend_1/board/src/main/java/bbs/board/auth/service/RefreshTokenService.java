package bbs.board.auth.service;

import bbs.board.auth.entity.RefreshToken;
import bbs.board.auth.repository.RefreshTokenRepository;
import bbs.board.domain.Member;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * date           : 2024-12-13
 * created by     : 임경재
 * description    :
 */
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final Long refreshTokenDurationMs;

    public RefreshTokenService(final RefreshTokenRepository refreshTokenRepository, final MemberRepository memberRepository, @Value("${app.auth.refreshTokenExpirationMs}") final Long refreshTokenDurationMs) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.memberRepository = memberRepository;
        this.refreshTokenDurationMs = refreshTokenDurationMs;
    }

    public RefreshToken createRefreshToken(String email) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        RefreshToken refreshToken = new RefreshToken(
                findMember,
                UUID.randomUUID().toString(),
                Instant.now().plusMillis(refreshTokenDurationMs)
        );

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * 토큰 문자열로 RefreshToken 엔티티를 조회
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Refresh 토큰 만료 여부 검증. 만료 시 DB에서 제거 및 예외 발생
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token has expired. Please login again.");
        }
        return token;
    }

}
