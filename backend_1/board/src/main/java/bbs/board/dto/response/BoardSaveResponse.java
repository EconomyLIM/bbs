package bbs.board.dto.response;

import bbs.board.dto.common.ResponseDTO;
import lombok.Getter;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
public class BoardSaveResponse extends ResponseDTO {

    private Long boardId;

    private BoardSaveResponse() {
    }

    private BoardSaveResponse(Long boardId) {
        super();
        this.boardId = boardId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public static BoardSaveResponse of(Long boardId) {
        return new BoardSaveResponse(boardId);
    }
}
