package bbs.board.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * date           : 2024-12-24
 * created by     : 임경재
 * description    :
 */
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    // 블랙리스트 등록 (Access Token)
    public void setLogoutToken(String token, long expiration) {
        // token 을 key로, "logout" 값을 저장, expiration 초 후에 자동 삭제
        redisTemplate.opsForValue().set(token, "logout", expiration, TimeUnit.SECONDS);
    }

    // 블랙리스트 조회
    public boolean isLogoutToken(String token) {
        return redisTemplate.hasKey(token);
    }

    // Refresh Token 저장 (사용자별 관리 시: key=userId, value=RefreshToken)
    public void saveRefreshToken(String userId, String refreshToken, long expiration) {
        redisTemplate.opsForValue().set(userId, refreshToken, expiration, TimeUnit.SECONDS);
    }

    // Refresh Token 조회
    public String getRefreshToken(String userId) {
        Object value = redisTemplate.opsForValue().get(userId);
        return value != null ? value.toString() : null;
    }

    // Refresh Token 삭제
    public void deleteRefreshToken(String email) {
        redisTemplate.delete(email);
    }
}
