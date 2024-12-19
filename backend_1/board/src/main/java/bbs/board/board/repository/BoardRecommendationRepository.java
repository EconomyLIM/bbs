package bbs.board.board.repository;

import bbs.board.board.entity.BoardRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * date           : 2024-11-29
 * created by     : 임경재
 * description    :
 */
public interface BoardRecommendationRepository extends JpaRepository<BoardRecommendation, Long> {

    @Query("select b from BoardRecommendation b where b.board.id = :boardId and b.member.email = :email")
    BoardRecommendation findByEmailAndBoard(String email, Long boardId);
}
