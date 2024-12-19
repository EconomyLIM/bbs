package bbs.board.comment.dto;

import bbs.board.common.RecommendationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
public class CommentRecommendationRequest {

    private Long commentId;
    private String memberEmail;
    private RecommendationType recommendationType;
}
