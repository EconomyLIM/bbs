package bbs.board.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * date           : 2024-12-11
 * created by     : 임경재
 * description    :
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class SaveCommentRequest {
    private Long boardId;
    private String memberEmail;
    private Long commentId;
    private Long parentCommentId;
    private String commentContent;
}
