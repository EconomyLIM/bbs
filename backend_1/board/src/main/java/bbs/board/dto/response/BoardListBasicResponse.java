package bbs.board.dto.response;

import bbs.board.dto.common.BasicResponse;
import lombok.Getter;

import java.util.List;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
@Getter
public class BoardListBasicResponse extends BasicResponse {
    private int currentPage;
    private int totalPage;
    private List<BoardResponse> list;

    private BoardListBasicResponse() {
    }

    private BoardListBasicResponse(List<BoardResponse> list, int currentPage, int totalPage) {
        super();
        this.list = list;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }

    public static BoardListBasicResponse of(final List<BoardResponse> list, final int currentPage, final int totalPage) {
        return new BoardListBasicResponse(list, currentPage, totalPage);
    }
}
