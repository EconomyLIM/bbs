package bbs.board.dto.response;

import bbs.board.dto.common.ResponseDTO;
import lombok.Getter;

import java.util.List;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
@Getter
public class BoardListResponse extends ResponseDTO {
    private List<BoardResponseDTO> list;

    private BoardListResponse() {
    }

    private BoardListResponse(List<BoardResponseDTO> list) {
        super();
        this.list = list;
    }

    public static BoardListResponse of(final List<BoardResponseDTO> list){
        return new BoardListResponse(list);
    }
}
