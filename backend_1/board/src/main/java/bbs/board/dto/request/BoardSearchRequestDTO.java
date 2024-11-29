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
    public int page;

    public String memberId;
    public String searchKeyword;
}
