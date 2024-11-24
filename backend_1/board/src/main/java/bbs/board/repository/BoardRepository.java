package bbs.board.repository;

import bbs.board.dao.Board;
import bbs.board.dto.BoardDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardRepository {

    private final EntityManager em;

    @Transactional
    public Long save(Board board){
        em.persist(board);
        return board.getId();
    }

    @Transactional
    public Long update(BoardDTO boardDTO){
        Board board = em.find(Board.class, boardDTO.getId());
        board.update(boardDTO);
        return board.getId();
    }

    public Board findById(BoardDTO boardDTO){
        return em.find(Board.class, boardDTO.getId());
    }

    public List<Board> findAll (){
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }
}
