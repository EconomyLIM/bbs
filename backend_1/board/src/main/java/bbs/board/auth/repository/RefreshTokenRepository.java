package bbs.board.auth.repository;

import bbs.board.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * date           : 2024-12-13
 * created by     : 임경재
 * description    :
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
    int deleteByMember_Id(Long memberId); // 특정 유저의 모든 리프레시 토큰 삭제용
}
