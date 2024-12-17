package bbs.board.dto.request;

import bbs.board.dto.common.RecommendationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * date           : 2024-11-29
 * created by     : 임경재
 * description    :
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class BoardLikedDTO {

    private Long memberId;
    private String memberEmail;
    private Long boardId;
    private RecommendationType recommendationType;
}
