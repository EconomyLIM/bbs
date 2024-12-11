package bbs.board.dto.response;

import bbs.board.domain.Comment;
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

    public static FindCommentByBoardBasicResponse of(final List<Comment> comments) {
        List<CommentDTO> list = comments
                .stream()
                .map(CommentDTO::new)
                .toList();
        return new FindCommentByBoardBasicResponse(list);
    }
}
