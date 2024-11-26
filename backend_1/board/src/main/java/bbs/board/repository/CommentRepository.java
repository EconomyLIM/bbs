package bbs.board.repository;

import bbs.board.domain.Board;
import bbs.board.domain.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save (Comment comment){
        em.persist(comment);
    }

    public List<Comment> getCommentsInBoard(Board board){
//        return em.createQuery("select c from Comment c where c.board = :board", Comment.class)
//                .setParameter("board", board).getResultList();

        return em.createQuery(
                        "select c from Comment c " +
                                "left join fetch c.replies " +
                                "where c.board = :board and c.parentComment is null " +
                                "order by c.registerDate", Comment.class)
                .setParameter("board", board)
                .getResultList();
    }
}
