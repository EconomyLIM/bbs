package bbs.board.dto.response;

import bbs.board.domain.Comment;
import lombok.Data;

import java.util.List;

/**
 * date           : 2024-12-11
 * created by     : 임경재
 * description    :
 */
@Data
public class CommentDTO {
    private Long commentId;
    private String commentContent;
    private String memberEmail;
    private List<CommentDTO> childComments;

    public CommentDTO(Comment comment) {
        this.commentId = comment.getId();
        this.commentContent = comment.getCommentContent();
        this.memberEmail = comment.getMember().getEmail();
    }
}
