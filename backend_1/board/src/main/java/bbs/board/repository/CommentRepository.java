package bbs.board.repository;

import bbs.board.domain.Board;
import bbs.board.domain.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

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

    public void deleteComment(Comment comment){
        em.remove(comment);
    }

    public Comment findById(long id){
        return em.find(Comment.class, id);
    }
}
