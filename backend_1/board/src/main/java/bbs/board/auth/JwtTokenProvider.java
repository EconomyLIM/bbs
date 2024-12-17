package bbs.board.auth;

import bbs.board.auth.entity.RefreshToken;
import bbs.board.auth.service.RefreshTokenService;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * date           : 2024-12-13
 * created by     : 임경재
 * description    :
 */
@Component
@Slf4j
public class JwtTokenProvider {

    private final Key key;
    private final long tokenExpirationMs;
    private final RefreshTokenService refreshTokenService;

    public JwtTokenProvider(@Value("${app.auth.tokenSecret}") String tokenSecret,
                            @Value("${app.auth.tokenExpirationMs}") long tokenExpirationMs,
                            RefreshTokenService refreshTokenService) {
        this.tokenExpirationMs = tokenExpirationMs;
        this.key = Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));
        this.refreshTokenService = refreshTokenService;
    }

    public String createToken(String email, String nickname) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpirationMs);

        return Jwts.builder()
                .setSubject(email)
                .claim("nickname", nickname)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token, String email) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);


            return true;
        } catch (ExpiredJwtException e) {
            RefreshToken refreshToken = refreshTokenService.findRefreshTokenByEmail(email);
            if (refreshToken == null) {
                throw new CustomException(ErrorCode.INVALID_AUTHENTICATION);
            }

            refreshTokenService.verifyExpiration(refreshToken);
            throw new CustomException(ErrorCode.INVALID_AUTHENTICATION);
        } catch (JwtException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
            throw new CustomException(ErrorCode.INVALID_AUTHENTICATION);
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getClaimFromToken(final String keyName, final String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get(keyName, String.class); // nickname 추출
    }
}
