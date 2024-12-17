package bbs.board.comment.repository;

import bbs.board.comment.entity.CommentRecommendation;
import bbs.board.domain.Board;
import bbs.board.comment.entity.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    @Transactional
    public void save (Comment comment){
        em.persist(comment);
    }

    public List<Comment> findByBoard (Board board){
        return em.createQuery("select c from Comment c where c.board = :board", Comment.class)
                .setParameter("board", board)
                .getResultList();
    }

    public List<Comment> getCommentsInBoard(Board board){

        return em.createQuery(
                        "select c from Comment c " +
                                "left join fetch c.replies " +
                                "where c.board = :board and c.parentComment is null " , Comment.class)
                .setParameter("board", board)
                .getResultList();
    }

    public Comment findById(long id){
        return em.find(Comment.class, id);
    }

    @Transactional
    public void deleteComment(Comment comment){
        em.remove(comment);
    }

    public CommentRecommendation findByEmailAndBoard(String email, Long commentId){
        List<CommentRecommendation> resultList = em.createQuery("select c from CommentRecommendation c where c.member.email =:email and c.comment.id = :commentId", CommentRecommendation.class)
                .setParameter("email", email)
                .setParameter("commentId", commentId)
                .getResultList();

        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public void save(CommentRecommendation commentRecommendation){
        em.persist(commentRecommendation);
    }


}
