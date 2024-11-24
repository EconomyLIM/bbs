package bbs.board.repository;

import bbs.board.dao.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save (Comment comment){
        em.persist(comment);
    }
}
