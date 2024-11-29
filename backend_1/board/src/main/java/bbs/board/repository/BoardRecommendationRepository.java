package bbs.board.repository;

import bbs.board.domain.BoardRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * date           : 2024-11-29
 * created by     : 임경재
 * description    :
 */
public interface BoardRecommendationRepository extends JpaRepository<BoardRecommendation, Long> {

    @Query("select b from BoardRecommendation b where b.board.id = :boardId and b.member.id = :memberId")
    BoardRecommendation findByEmailAndBoard(Long memberId, Long boardId);
}
