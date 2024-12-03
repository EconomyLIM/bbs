package bbs.board.repository;

import bbs.board.domain.Board;
import bbs.board.dto.request.BoardSearchRequestDTO;

import java.util.List;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
public interface BoardCustomRepository {
    List<Board> findBoardBySearch(BoardSearchRequestDTO dto);
}
