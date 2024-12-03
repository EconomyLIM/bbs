package bbs.board.dto.common;

import lombok.Getter;

/**
 * date           : 2024-11-29
 * created by     : 임경재
 * description    :
 */
@Getter
public enum CommonPage {
    BOARD_PAGE_SIZE(20)
    ;

    int pageSize;

    CommonPage() {
    }

    CommonPage(int pageSize) {
        this.pageSize = pageSize;
    }
}
