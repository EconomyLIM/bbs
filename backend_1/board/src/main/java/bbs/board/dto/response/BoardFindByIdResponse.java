package bbs.board.dto.response;

import bbs.board.domain.Board;
import bbs.board.dto.common.ResponseDTO;
import lombok.Getter;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
@Getter
public class BoardFindByIdResponse extends ResponseDTO {
    private Board board;

    private BoardFindByIdResponse() {
    }

    private BoardFindByIdResponse(Board board) {
        this.board = board;
    }

    public static BoardFindByIdResponse of(Board board) {
       return new BoardFindByIdResponse(board);
    }
}
