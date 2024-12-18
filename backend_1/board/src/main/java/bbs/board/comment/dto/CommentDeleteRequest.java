package bbs.board.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * date           : 2024-12-18
 * created by     : 임경재
 * description    :
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class CommentDeleteRequest {

    private Long commentId;
    private String memberEmail;
}
