package bbs.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * date           : 2024-11-29
 * created by     : 임경재
 * description    :
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class BoardSearchRequestDTO {
    private int page;

    private String memberId;
    private String title;
    private String searchWord;
    private String searchKeyword;
}
