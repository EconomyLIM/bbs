package bbs.board.comment.repository;

import bbs.board.comment.entity.CommentRecommendation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * date           : 2024-12-18
 * created by     : 임경재
 * description    :
 */
@Repository
@RequiredArgsConstructor
public class CommentRecommendationRepositoryImpl implements CustomCommentRecommendationRepository {
    private final EntityManager em;

    @Override
    public CommentRecommendation findByEmailAndBoard(final String email, final Long commentId) {
        List<CommentRecommendation> resultList = em.createQuery("select c from CommentRecommendation c where c.member.email =:email and c.comment.id = :commentId", CommentRecommendation.class)
                .setParameter("email", email)
                .setParameter("commentId", commentId)
                .getResultList();

        return resultList.isEmpty() ? null : resultList.get(0);
    }
}
