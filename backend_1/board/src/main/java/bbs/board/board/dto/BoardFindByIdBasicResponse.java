package bbs.board.board.dto;

import bbs.board.board.entity.Board;
import bbs.board.common.dto.BasicResponse;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

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

    public static BoardFindByIdBasicResponse of(Board board, String currentLoginEmail) {
        BoardDTO boardDTO = BoardDTO
                .builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .memberEmail(board.getMember().getEmail())
                .nickname(board.getMember().getNickname())
                .likedCnt(board.getLikedCnt())
                .isMine(board.getMember().getEmail().equals(currentLoginEmail))
                .categoryId(board.getCategory().getId())
                .categoryName(board.getCategory().getCategoryName())
                .registeredDate(board.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();

       return new BoardFindByIdBasicResponse(boardDTO);
    }


}
