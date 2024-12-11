package bbs.board.dto.response;

import bbs.board.domain.Board;
import bbs.board.dto.BoardDTO;
import bbs.board.dto.common.BasicResponse;
import lombok.Getter;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
@Getter
public class BoardFindByIdBasicResponse extends BasicResponse {
    private BoardDTO board;

    private BoardFindByIdBasicResponse() {
    }

    private BoardFindByIdBasicResponse(BoardDTO board) {
        this.board = board;
    }

    public static BoardFindByIdBasicResponse of(Board board) {
        BoardDTO boardDTO = BoardDTO
                .builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .memberEmail(board.getMember().getEmail())
                .likedCnt(board.getLikedCnt())
                .build();

       return new BoardFindByIdBasicResponse(boardDTO);
    }
}
