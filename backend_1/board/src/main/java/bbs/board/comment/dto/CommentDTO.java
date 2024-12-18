package bbs.board.comment.dto;

import bbs.board.comment.entity.Comment;
import bbs.board.comment.entity.CommentStatus;
import lombok.Data;

import java.time.format.DateTimeFormatter;
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
    private boolean mine;
    private int likedCnt;
    private List<CommentDTO> childComments;
    private CommentStatus status;

    public CommentDTO(Comment comment) {
        this.commentId = comment.getId();
        this.commentContent = comment.getCommentContent();
        this.memberEmail = comment.getMember().getEmail();
        if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            this.childComments = comment.getReplies().stream().map(CommentDTO::new).toList();
        }
        this.nickname = comment.getMember().getNickname();
        this.registered = comment.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.likedCnt = comment.getLikedCnt();
        this.status = comment.getStatus();
    }
}
