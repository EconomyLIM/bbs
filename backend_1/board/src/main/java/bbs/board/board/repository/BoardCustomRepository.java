package bbs.board.board.repository;

import bbs.board.board.entity.Board;
import bbs.board.board.dto.BoardSearchRequestDTO;

import java.util.List;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
public interface BoardCustomRepository {
    List<Board> findBoardBySearch(BoardSearchRequestDTO dto);

    int findBoardBySearchCnt(BoardSearchRequestDTO dto);
}
