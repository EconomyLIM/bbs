package bbs.board.comment.repository;

import bbs.board.comment.entity.CommentRecommendation;

/**
 * date           : 2024-12-18
 * created by     : 임경재
 * description    :
 */
public interface CustomCommentRecommendationRepository {
     CommentRecommendation findByEmailAndBoard(String email, Long commentId);
}
