package bbs.board.board.repository;

import bbs.board.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {

    @Query("select b from Board b join fetch b.member m join fetch b.category c where b.id = :id")
    Optional<Board> findJoinFetchBoardById(Long id);
}
