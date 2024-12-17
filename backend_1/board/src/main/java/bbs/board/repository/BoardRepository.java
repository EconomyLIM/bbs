package bbs.board.repository;

import bbs.board.domain.Board;
import bbs.board.dto.BoardDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {

    @Query("select b from Board b join fetch b.member m join fetch b.category c where b.id = :id")
    Optional<Board> findJoinFetchBoardById(Long id);
}
