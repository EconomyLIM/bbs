package bbs.board.dto.response;

import bbs.board.dto.common.BasicResponse;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
public class BoardSaveBasicResponse extends BasicResponse {

    private Long boardId;

    private BoardSaveBasicResponse() {
    }

    private BoardSaveBasicResponse(Long boardId) {
        super();
        this.boardId = boardId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public static BoardSaveBasicResponse of(Long boardId) {
        return new BoardSaveBasicResponse(boardId);
    }
}
