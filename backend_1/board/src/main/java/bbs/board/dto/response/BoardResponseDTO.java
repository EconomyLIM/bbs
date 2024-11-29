package bbs.board.dto.response;

import bbs.board.domain.Board;
import bbs.board.domain.Category;
import bbs.board.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * date           : 2024-11-29
 * created by     : 임경재
 * description    :
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class BoardResponseDTO {
    private Long id;
    private String title;
    private String content;
    private int likedCnt;
    private Member member;
    private Category category;

    public BoardResponseDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.likedCnt = board.getLikedCnt();
        this.member = board.getMember();
        this.category = board.getCategory();
    }
}
