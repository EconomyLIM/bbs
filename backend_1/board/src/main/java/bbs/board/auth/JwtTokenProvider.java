package bbs.board.auth;

import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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

    public JwtTokenProvider(@Value("${app.auth.tokenSecret}") String tokenSecret,
                            @Value("${app.auth.tokenExpirationMs}") long tokenExpirationMs) {
        this.tokenExpirationMs = tokenExpirationMs;
        this.key = Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpirationMs);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
            throw new CustomException(ErrorCode.INVALID_AUTHENTICATION);

        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
