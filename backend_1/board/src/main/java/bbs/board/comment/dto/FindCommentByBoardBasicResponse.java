package bbs.board.comment.dto;

import bbs.board.comment.entity.Comment;
import bbs.board.dto.common.BasicResponse;
import lombok.Getter;

import java.util.List;

/**
 * date           : 2024-12-11
 * created by     : 임경재
 * description    :
 */
@Getter
public class FindCommentByBoardBasicResponse extends BasicResponse {
    private List<CommentDTO> comments;

    protected FindCommentByBoardBasicResponse() {
    }

    protected FindCommentByBoardBasicResponse(final List<CommentDTO> comments) {
        this.comments = comments;
    }

    public static FindCommentByBoardBasicResponse of(final List<Comment> comments, final String memberEmail) {
        List<CommentDTO> list = comments
                .stream()
                .map(CommentDTO::new)
                .toList();

        if (memberEmail != null) {
            mineSetting(memberEmail, list);
        }

        return new FindCommentByBoardBasicResponse(list);
    }

    private static void mineSetting(final String memberEmail, final List<CommentDTO> list) {

        for (CommentDTO commentDTO : list) {
            if (commentDTO.getMemberEmail().equals(memberEmail)) {
                commentDTO.setMine(true);
            }

            if (commentDTO.getChildComments()!= null && !commentDTO.getChildComments().isEmpty()) {
                for (CommentDTO childComment : commentDTO.getChildComments()) {
                    if (childComment.getMemberEmail().equals(memberEmail)) {
                        childComment.setMine(true);
                    }
                }
            }

        }
    }
}
