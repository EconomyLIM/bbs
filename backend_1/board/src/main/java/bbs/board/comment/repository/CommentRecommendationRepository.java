package bbs.board.comment.repository;

import bbs.board.comment.entity.CommentRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * date           : 2024-12-18
 * created by     : 임경재
 * description    :
 */
public interface CommentRecommendationRepository extends JpaRepository<CommentRecommendation, Long>, CustomCommentRecommendationRepository {
}
