package bbs.board.dto.response;

import bbs.board.domain.Comment;
import lombok.Data;

import java.util.ArrayList;
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
    private String nickname;
    private String registered;
    private List<CommentDTO> childComments;

    public CommentDTO(Comment comment) {
        this.commentId = comment.getId();
        this.commentContent = comment.getCommentContent();
        this.memberEmail = comment.getMember().getEmail();
        if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            this.childComments = comment.getReplies().stream().map(CommentDTO::new).toList();
        }
    }
}
