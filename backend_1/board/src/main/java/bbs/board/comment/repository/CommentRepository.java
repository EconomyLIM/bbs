package bbs.board.comment.repository;

import bbs.board.board.entity.Board;
import bbs.board.comment.entity.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Optional<Comment> findById(long id){
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Transactional
    public void deleteComment(Comment comment){
        em.remove(comment);
    }

}
