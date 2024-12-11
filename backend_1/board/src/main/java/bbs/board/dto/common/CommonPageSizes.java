package bbs.board.dto.common;

import lombok.Getter;

/**
 * date           : 2024-11-29
 * created by     : 임경재
 * description    :
 */
@Getter
public enum CommonPageSizes {
    BOARD_PAGE_SIZE(20)
    , COMMENT_PAGE_SIZE(30)
    ;

    int pageSize;

    CommonPageSizes() {
    }

    CommonPageSizes(int pageSize) {
        this.pageSize = pageSize;
    }
}
